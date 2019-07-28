/******************************************************************************
 *                                                                             
 *                      Woodare PROPRIETARY INFORMATION                        
 *                                                                             
 *          The information contained herein is proprietary to Woodare         
 *           and shall not be reproduced or disclosed in whole or in part      
 *                    or used for any design or manufacture                    
 *              without direct written authorization from FengDa.              
 *                                                                             
 *            Copyright (c) 2013 by Woodare.  All rights reserved.             
 *                                                                             
 *****************************************************************************/
package com.wang.wangblog.utils;

/**
 * $Id: UnZipper.java 10 2009-11-26 13:46:20Z oneyour $
 * 
 * UnZipper.java
 * This is an accompanying program for the article
 * http://www.1your.com/drupal/unziparchivedorcompressedfilesinJava
 * 
 * Copyright (c) 2009 - 2010 www.1your.com.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of www.1your.com nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * A Java program to unzip a ZIP archive. The ZIP archive file name is got from
 * the user through the System.in input stream
 */
public class UnZipper {
	/**
	 * Private Constructor as this is a utility class with only public static
	 * methods
	 */
	private UnZipper() {
	}

	public static void main(String[] arg) throws IOException {
		UnZipper.unzip("C:\\Users\\lu_feng\\Desktop\\ipa\\app2.ipa", "C:\\Users\\lu_feng\\Desktop\\aaa");
	}

	/**
	 * @param mkdirName
	 */
	private static void makeDir(File f) {
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
	}

	/**
	 * The core Business Logic method that extracts a ZIP file maintaining the
	 * folder structure
	 * 
	 * @param zipFileName
	 *            The name of the ZIP file to be extracted
	 * @param directory
	 *            The file directory
	 * @throws IOException
	 *             Problems while extacting the ZIP file
	 */
	public static void unzip(String zipFileName, String directory) throws IOException {
		ZipFile zipFile = null;
		InputStream inputStream = null;

		File inputFile = new File(zipFileName);
		try {
			// Wrap the input file with a ZipFile to iterate through
			// its contents
			zipFile = new ZipFile(inputFile);
			Enumeration<? extends ZipEntry> oEnum = zipFile.entries();
			while (oEnum.hasMoreElements()) {
				ZipEntry zipEntry = oEnum.nextElement();
				File file = new File(directory + File.separator + zipEntry.getName());

				makeDir(file);

				if (zipEntry.isDirectory()) {
					// If the entry in the ZIP file is a directory
					// then create the directory
					file.mkdirs();
				}
				else {
					// If the entry in the ZIP file is a file
					// then write the file in the appropriate
					// directory location (as it is in the ZIP file)
					inputStream = zipFile.getInputStream(zipEntry);
					write(inputStream, file);
				}
			}
		} catch (IOException ioException) {
			throw ioException;
		} finally {
			// Clean up the I/O
			try {
				if (zipFile != null) {
					zipFile.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException problemsDuringClose) {
				System.out.println("Problems during cleaning up the I/O.");
			}
		}
	}

	/**
	 * Writes to the supplied file with the contents read from the supplied
	 * input stream.
	 * 
	 * @param inputStream
	 *            The Source input stream from where the contents will be read
	 *            to write to the file.
	 * @param fileToWrite
	 *            The file to which the contents from the input stream will be
	 *            written to.
	 * @throws IOException
	 *             Any problems while reading from the input stream or writing
	 *             to the file.
	 */
	public static void write(InputStream inputStream, File fileToWrite) throws IOException {

		BufferedInputStream buffInputStream = new BufferedInputStream(inputStream);
		FileOutputStream fos = new FileOutputStream(fileToWrite);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		// write bytes
		int byteData;
		while ((byteData = buffInputStream.read()) != -1) {
			bos.write((byte) byteData);
		}

		// close all the open streams
		bos.close();
		fos.close();
		buffInputStream.close();
	}
}
