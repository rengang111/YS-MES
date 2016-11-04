package com.ys.util;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ys.system.common.BusinessConstants;

//commented code blocks are only used for CORS environments
public class UploadReceiver extends HttpServlet	
{
	private static final File UPLOAD_DIR = new File("/uploads/");
    private static File TEMP_DIR = new File("/uploads/");

    private static String CONTENT_LENGTH = "Content-Length";
    private static int SUCCESS_RESPONSE_CODE = 200;

    final Logger log = LoggerFactory.getLogger(UploadReceiver.class);
    
    @Override
    public void init() throws ServletException
    {
        UPLOAD_DIR.mkdirs();
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String uuid = req.getPathInfo().replaceAll("/", "");

        handleDeleteFileRequest(uuid, resp);
    }

    private void handleDeleteFileRequest(String uuid, HttpServletResponse resp) throws IOException
    {
        FileUtils.deleteDirectory(new File(UPLOAD_DIR, uuid));

        if (new File(UPLOAD_DIR, uuid).exists())
        {
            log.warn("couldn't find or delete " + uuid);
        }
        else
        {
            log.info("deleted " + uuid);
        }

        resp.setStatus(SUCCESS_RESPONSE_CODE);
//        resp.addHeader("Access-Control-Allow-Origin", "*");
    }

    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(SUCCESS_RESPONSE_CODE);
        resp.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8090");
//        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Methods", "POST, DELETE");
        resp.addHeader("Access-Control-Allow-Headers", "x-requested-with, cache-control, content-type");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        RequestParser requestParser = null;

        boolean isIframe = req.getHeader("X-Requested-With") == null || !req.getHeader("X-Requested-With").equals("XMLHttpRequest");

        try
        {
//            resp.setContentType(isIframe ? "text/html" : "text/plain");
            resp.setContentType("text/plain");
            resp.setStatus(SUCCESS_RESPONSE_CODE);

//            resp.addHeader("Access-Control-Allow-Origin", "http://192.168.130.118:8080");
//            resp.addHeader("Access-Control-Allow-Credentials", "true");
//            resp.addHeader("Access-Control-Allow-Origin", "*");            
            
            String dir = req.getParameter("key");
            //String aid = req.getParameter("aid");
            
            MultipartHttpServletRequest fileRequest = (MultipartHttpServletRequest)req;
            List<MultipartFile> files = fileRequest.getFiles("qqfile");
            
            for (MultipartFile myfile : files)
            { 
                String path = myfile.getOriginalFilename();
                System.out.println(path);
                String fileName = path;
                InputStream is = myfile.getInputStream();
                
                String ctxPath = req.getSession().getServletContext().getRealPath("/")
        				+ BusinessConstants.BUSINESSPHOTOPATH + "/" + dir + "/";  
                
                writeFile(is, new File(ctxPath, fileName), null);
                
                ImageUtil.scale(ctxPath, fileName,280,210,"small");
                //path = FileManager.T_MAIL_ATTACHMENT_PATH + folder + File.separator + fileName;
                //uploadfiles(path, is);// �ϴ��ļ�
            }
            
            return;

//            if (ServletFileUpload.isMultipartContent(req))
//            {
//            	ServletContext servletContext=req.getSession().getServletContext(); 
//            	
//            	MultipartUploadParser multipartUploadParser = new MultipartUploadParser(req, TEMP_DIR, servletContext);
//                requestParser = RequestParser.getInstance(req, multipartUploadParser);
//                writeFileForMultipartRequest(requestParser);
//                writeResponse(resp.getWriter(), requestParser.generateError() ? "Generated error" : null, isIframe, false, requestParser);
//            }
//            else
//            {
//                requestParser = RequestParser.getInstance(req, null);
//
//                //handle POST delete file request
//                if (requestParser.getMethod() != null
//                        && requestParser.getMethod().equalsIgnoreCase("DELETE"))
//                {
//                    String uuid = requestParser.getUuid();
//                    handleDeleteFileRequest(uuid, resp);
//                }
//                else
//                {
//                    writeFileForNonMultipartRequest(req, requestParser);
//                    writeResponse(resp.getWriter(), requestParser.generateError() ? "Generated error" : null, isIframe, false, requestParser);
//                }
//            }
        } catch (Exception e)
        {
            log.error("Problem handling upload request", e);
            if (e instanceof MergePartsException)
            {
                writeResponse(resp.getWriter(), e.getMessage(), isIframe, true, requestParser);
            }
            else
            {
                writeResponse(resp.getWriter(), e.getMessage(), isIframe, false, requestParser);
            }
        }
    }

    private void writeFileForNonMultipartRequest(HttpServletRequest req, RequestParser requestParser) throws Exception
    {
        File dir = new File(UPLOAD_DIR, requestParser.getUuid());
    	
        dir.mkdirs();

        String contentLengthHeader = req.getHeader(CONTENT_LENGTH);
        long expectedFileSize = Long.parseLong(contentLengthHeader);

        if (requestParser.getPartIndex() >= 0)
        {
            writeFile(req.getInputStream(), new File(dir, requestParser.getUuid() + "_" + String.format("%05d", requestParser.getPartIndex())), null);

            if (requestParser.getTotalParts()-1 == requestParser.getPartIndex())
            {
                File[] parts = getPartitionFiles(dir, requestParser.getUuid());
                File outputFile = new File(dir, requestParser.getFilename());
                for (File part : parts)
                {
                    mergeFiles(outputFile, part);
                }

                assertCombinedFileIsVaid(requestParser.getTotalFileSize(), outputFile, requestParser.getUuid());
                deletePartitionFiles(dir, requestParser.getUuid());
            }
        }
        else
        {
            writeFile(req.getInputStream(), new File(dir, requestParser.getFilename()), expectedFileSize);
        }
    }


    private void writeFileForMultipartRequest(RequestParser requestParser) throws Exception
    {
        File dir = new File(UPLOAD_DIR, requestParser.getUuid());
        dir.mkdirs();

        if (requestParser.getPartIndex() >= 0)
        {
            writeFile(requestParser.getUploadItem().getInputStream(), new File(dir, requestParser.getUuid() + "_" + String.format("%05d", requestParser.getPartIndex())), null);

            if (requestParser.getTotalParts()-1 == requestParser.getPartIndex())
            {
                File[] parts = getPartitionFiles(dir, requestParser.getUuid());
                File outputFile = new File(dir, requestParser.getOriginalFilename());
                for (File part : parts)
                {
                    mergeFiles(outputFile, part);
                }

                assertCombinedFileIsVaid(requestParser.getTotalFileSize(), outputFile, requestParser.getUuid());
                deletePartitionFiles(dir, requestParser.getUuid());
            }
        }
        else
        {
            writeFile(requestParser.getUploadItem().getInputStream(), new File(dir, requestParser.getFilename()), null);
        }
    }

    private void assertCombinedFileIsVaid(long totalFileSize, File outputFile, String uuid) throws MergePartsException
    {
        if (totalFileSize != outputFile.length())
        {
            deletePartitionFiles(UPLOAD_DIR, uuid);
            outputFile.delete();
            throw new MergePartsException("Incorrect combined file size!");
        }

    }


    private static class PartitionFilesFilter implements FilenameFilter
    {
        private String filename;
        PartitionFilesFilter(String filename)
        {
            this.filename = filename;
        }

        @Override
        public boolean accept(File file, String s)
        {
            return s.matches(Pattern.quote(filename) + "_\\d+");
        }
    }

    private static File[] getPartitionFiles(File directory, String filename)
    {
        File[] files = directory.listFiles(new PartitionFilesFilter(filename));
        Arrays.sort(files);
        return files;
    }

    private static void deletePartitionFiles(File directory, String filename)
    {
        File[] partFiles = getPartitionFiles(directory, filename);
        for (File partFile : partFiles)
        {
            partFile.delete();
        }
    }

    private File mergeFiles(File outputFile, File partFile) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(outputFile, true);

        try
        {
            FileInputStream fis = new FileInputStream(partFile);

            try
            {
                IOUtils.copy(fis, fos);
            }
            finally
            {
                IOUtils.closeQuietly(fis);
            }
        }
        finally
        {
            IOUtils.closeQuietly(fos);
        }

        return outputFile;
    }

    private File writeFile(InputStream in, File out, Long expectedFileSize) throws IOException
    {
        FileOutputStream fos = null;
        
        File dir = out.getParentFile(); 
        
        if(dir!=null&&!dir.exists()){
        	dir.mkdirs();
        } 

        try
        {
            fos = new FileOutputStream(out);

            IOUtils.copy(in, fos);

            if (expectedFileSize != null)
            {
                Long bytesWrittenToDisk = out.length();
                if (!expectedFileSize.equals(bytesWrittenToDisk))
                {
                    log.warn("Expected file {} to be {} bytes; file on disk is {} bytes", new Object[] { out.getAbsolutePath(), expectedFileSize, 1 });
                    out.delete();
                    throw new IOException(String.format("Unexpected file size mismatch. Actual bytes %s. Expected bytes %s.", bytesWrittenToDisk, expectedFileSize));
                }
            }

            return out;
        }
        catch (Exception e)
        {
            throw new IOException(e);
        }
        finally
        {
            IOUtils.closeQuietly(fos);
        }
    }

    private void writeResponse(PrintWriter writer, String failureReason, boolean isIframe, boolean restartChunking, RequestParser requestParser)
    {
        if (failureReason == null)
        {
//            if (isIframe)
//            {
//                writer.print("{\"success\": true, \"uuid\": \"" + requestParser.getUuid() + "\"}<script src=\"http://192.168.130.118:8080/client/js/iframe.xss.response.js\"></script>");
//            }
//            else
//            {
                writer.print("{\"success\": true}");
//            }
        }
        else
        {
            if (restartChunking)
            {
                writer.print("{\"error\": \"" + failureReason + "\", \"reset\": true}");
            }
            else
            {
//                if (isIframe)
//                {
//                    writer.print("{\"error\": \"" + failureReason + "\", \"uuid\": \"" + requestParser.getUuid() + "\"}<script src=\"http://192.168.130.118:8080/client/js/iframe.xss.response.js\"></script>");
//                }
//                else
//                {

                    writer.print("{\"error\": \"" + failureReason + "\"}");
//                }
            }
        }
    }

    private class MergePartsException extends Exception
    {
        MergePartsException(String message)
        {
            super(message);
        }
    }
    
    public String [] getFileNameList(String dir){
    	
    	File file = new File(dir);
        String [] fileName = file.list();
        return fileName;
        
    }
    
    public String getFirstFileName(String dir){
    	
    	File file = new File(dir);
        String [] fileName = file.list();
        
        if(fileName==null||fileName.length==0){
        	return "";
        }else{
        	return fileName[0];
        }
        
    }
    
    public String getNowUseFileName(String dir,String filename){
    	    	
    	if (null==filename || filename.equals("")){
    		return getFirstFileName(dir);
    	}else{
    		
    		String file = dir + filename;
    		
    		File f = new File(file);
        	
        	if(f.exists()){
        		return filename;
        	}else{
        		//return "";
        		return getFirstFileName(dir);
        	}
    	}
        
    }
    
    public void deleteFile (HttpServletRequest request, String key, String fileName){
    	
    	String ctxPath = request.getServletContext().getRealPath("/");
    	
    	String file = ctxPath + BusinessConstants.BUSINESSPHOTOPATH.replace('/', '\\') + key + "\\" + fileName.replace('/', '\\');
    	String fileSmall = ctxPath + BusinessConstants.BUSINESSPHOTOPATH.replace('/', '\\') + key + "\\" +  BusinessConstants.BUSINESSSMALLPHOTOPATH.replace('/', '\\') + fileName.replace('/', '\\');

    	File f = new File(file); // ����Ҫɾ����ļ�λ��
    	if(f.exists()) {
    		f.delete(); 
    	}
    	
    	File fs = new File(fileSmall); // ����Ҫɾ����ļ�λ��
    	if(fs.exists()) {
    		fs.delete(); 
    	}
    }
    
    public void deleteFolder(HttpServletRequest request, String key) {
		String dir = request.getSession().getServletContext().getRealPath("/").replace('/', '\\')
				+ BusinessConstants.BUSINESSPHOTOPATH.replace('/', '\\') + key;     	
		String dirSmall = request.getSession().getServletContext().getRealPath("/").replace('/', '\\')
				+ BusinessConstants.BUSINESSPHOTOPATH.replace('/', '\\') + key + BusinessConstants.BUSINESSSMALLPHOTOPATH.replace('/', '\\'); 

		String [] fileNames = getFileNameList(dirSmall);
		
		if(fileNames!= null && fileNames.length == 0) {
			File f = new File(dirSmall); // ����Ҫɾ����ļ�λ��
	    	if(f.exists()) {
	    		f.delete(); 
	    	}
	    	
	    	 f = new File(dir); // ����Ҫɾ����ļ�λ��
		    	if(f.exists()) {
		    		f.delete(); 
		    	}
		}
    }
}
