/*
 * Copyright (C) 2015 Johnathan Louie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.jlouie.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract representation of file and directory pathnames.
 *
 * <p>
 * This a convenience class that combines the functionality of
 * {@code java.io.File}, {@code java.nio.file.Path}, and
 * {@code java.nio.file.Files}. All the documentation that applies to those
 * classes all apply here as this class just forwards the method calls.
 *
 * @author Johnathan Louie
 */
public class File implements Comparable<File> {

	private final java.io.File file;
	private final Path path;

	/**
	 * Creates a new <code>File</code> instance by converting the given pathname
	 * string into an abstract pathname. If the given string is the empty
	 * string, then the result is the empty abstract pathname.
	 *
	 * @param pathname A pathname string
	 * @throws NullPointerException If the <code>pathname</code> argument is
	 * <code>null</code>
	 */
	public File(String pathname) {
		if (pathname == null) {
			throw new NullPointerException();
		}
		this.file = new java.io.File(pathname);
		this.path = this.file.toPath();
	}

	/**
	 * Creates a new <code>File</code> instance based on a {@code java.io.File}
	 * object.
	 *
	 * @param file A {@code java.io.File} object
	 * @throws NullPointerException If the <code>file</code> argument is
	 * <code>null</code>
	 */
	public File(java.io.File file) {
		if (file == null) {
			throw new NullPointerException();
		}
		this.file = file;
		this.path = file.toPath();
	}

	/**
	 * Creates a new <code>File</code> instance based on a
	 * {@code java.nio.file.Path} object.
	 *
	 * @param path A {@code java.nio.file.Path} object
	 * @throws NullPointerException If the <code>path</code> argument is
	 * <code>null</code>
	 */
	public File(Path path) {
		if (path == null) {
			throw new NullPointerException();
		}
		this.file = path.toFile();
		this.path = path;
	}

	/**
	 * Converts the return statement of the other methods to <code>File</code>
	 * objects or <code>null</code>.
	 *
	 * @param other The object to convert
	 * @return A <code>File</code> object or <code>null</code> if the argument
	 * was <code>null</code>
	 */
	private File File(java.io.File other) {
		if (other == null) {
			return null;
		}
		return new File(other);
	}

	/**
	 * Converts the return statement of the other methods to <code>File</code>
	 * objects or <code>null</code>.
	 *
	 * @param other The object to convert
	 * @return A <code>File</code> object or <code>null</code> if the argument
	 * was <code>null</code>
	 */
	private File File(Path other) {
		if (other == null) {
			return null;
		}
		return new File(other);
	}

	public boolean canRead() {
		return file.canRead();
	}

	public boolean canWrite() {
		return file.canWrite();
	}

	public boolean canExecute() {
		return file.canExecute();
	}

	/**
	 * Atomically creates a new, empty file named by this abstract pathname if
	 * and only if a file with this name does not yet exist. The check for the
	 * existence of the file and the creation of the file if it does not exist
	 * are a single operation that is atomic with respect to all other
	 * filesystem activities that might affect the file.
	 * <P>
	 * Note: this method should <i>not</i> be used for file-locking, as the
	 * resulting protocol cannot be made to work reliably. The
	 * {@link java.nio.channels.FileLock FileLock} facility should be used
	 * instead.
	 *
	 * @return  <code>true</code> if the named file does not exist and was
	 * successfully created; <code>false</code> if the named file already exists
	 */
	public boolean createFile() {
		try {
			return file.createNewFile();
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Deletes the file or directory denoted by this abstract pathname. If this
	 * pathname denotes a directory, then the directory must be empty in order
	 * to be deleted.
	 *
	 * <p>
	 * Note that the {@link java.nio.file.Files} class defines the {@link
	 * java.nio.file.Files#delete(Path) delete} method to throw an
	 * {@link IOException} when a file cannot be deleted. This is useful for
	 * error reporting and to diagnose why a file cannot be deleted.
	 *
	 * @return <code>true</code> if and only if the file or directory is
	 * successfully deleted; <code>false</code> otherwise
	 *
	 * @throws SecurityException If a security manager exists and its
	 * <code>{@link java.lang.SecurityManager#checkDelete}</code> method denies
	 * delete access to the file
	 */
	public boolean delete() {
		return file.delete();
	}

	/**
	 * Tests whether the file or directory denoted by this abstract pathname
	 * exists.
	 *
	 * @return <code>true</code> if and only if the file or directory denoted by
	 * this abstract pathname exists; <code>false</code> otherwise
	 *
	 * @throws SecurityException If a security manager exists and its
	 * <code>{@link java.lang.SecurityManager#checkRead(java.lang.String)}</code>
	 * method denies read access to the file or directory
	 */
	public boolean exists() {
		return file.exists();
	}

	/**
	 * Returns the absolute form of this abstract pathname.
	 *
	 * @return The absolute abstract pathname denoting the same file or
	 * directory as this abstract pathname
	 *
	 * @throws SecurityException If a required system property value cannot be
	 * accessed.
	 */
	public File getAbsolute() {
		return File(file.getAbsoluteFile());
	}

	/**
	 * Returns the name of the file or directory denoted by this abstract
	 * pathname. This is just the last name in the pathname's name sequence. If
	 * the pathname's name sequence is empty, then the empty string is returned.
	 *
	 * @return The name of the file or directory denoted by this abstract
	 * pathname, or the empty string if this pathname's name sequence is empty
	 */
	public String getName() {
		return file.getName();
	}

	/**
	 * Returns the pathname string of this abstract pathname's parent, or
	 * <code>null</code> if this pathname does not name a parent directory.
	 *
	 * <p>
	 * The <em>parent</em> of an abstract pathname consists of the pathname's
	 * prefix, if any, and each name in the pathname's name sequence except for
	 * the last. If the name sequence is empty then the pathname does not name a
	 * parent directory.
	 *
	 * @return The pathname string of the parent directory named by this
	 * abstract pathname, or <code>null</code> if this pathname does not name a
	 * parent
	 */
	public File getParent() {
		return File(file.getParentFile());
	}

	/**
	 * Tests whether this abstract pathname is absolute. The definition of
	 * absolute pathname is system dependent. On UNIX systems, a pathname is
	 * absolute if its prefix is <code>"/"</code>. On Microsoft Windows systems,
	 * a pathname is absolute if its prefix is a drive specifier followed by
	 * <code>"\\"</code>, or if its prefix is <code>"\\\\"</code>.
	 *
	 * @return  <code>true</code> if this abstract pathname is absolute,
	 * <code>false</code> otherwise
	 */
	public boolean isAbsolute() {
		return file.isAbsolute();
	}

	public boolean isDirectory() {
		return file.isDirectory();
	}

	public boolean isFile() {
		return file.isFile();
	}

	public boolean isHidden() {
		return file.isHidden();
	}

	public long lastModified() {
		return file.lastModified();
	}

	public long sinceLastModified() {
		return System.currentTimeMillis() - lastModified();
	}

	public List<File> listFiles() {
		java.io.File[] oldFiles = file.listFiles();
		if (oldFiles == null) {
			return null;
		}
		List<File> newFiles = new ArrayList<File>();
		for (java.io.File i : oldFiles) {
			newFiles.add(new File(i));
		}
		return newFiles;
	}

	public boolean createDirectory() {
		return file.mkdir();
	}

	public boolean createDirectories() {
		return file.mkdirs();
	}

	public boolean move(File dest) {
		return file.renameTo(dest.file);
	}

	public boolean move(String dest) {
		return move(new File(dest));
	}

	@Override
	public String toString() {
		return file.toString();
	}

	@Override
	public int hashCode() {
		return file.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() == obj.getClass()) {
			File other = (File) obj;
			return file.equals(other.file);
		}
		return false;
	}

	public boolean createSymbolicLink(File link) {
		try {
			Files.createSymbolicLink(link.path, path);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean createSymbolicLink(String link) {
		return createSymbolicLink(new File(link));
	}

	public boolean isSymbolicLink() {
		return Files.isSymbolicLink(path);
	}

	public List<String> readAllLines() {
		try {
			return Files.readAllLines(path);
		} catch (Exception ex) {
			return null;
		}
	}

	public long size() {
		try {
			return Files.size(path);
		} catch (Exception ex) {
			return -1;
		}
	}

	public boolean append(Iterable<? extends CharSequence> lines) {
		try {
			Files.write(path, lines, StandardOpenOption.APPEND, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean write(Iterable<? extends CharSequence> lines) {
		try {
			Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean endsWith(File other) {
		return path.endsWith(other.path);
	}

	public boolean endsWith(String other) {
		return path.endsWith(other);
	}

	public File getNameElement(int index) {
		return File(path.getName(index));
	}

	public int getNameCount() {
		return path.getNameCount();
	}

	public File getRoot() {
		return File(path.getRoot());
	}

	public File normalize() {
		return File(path.normalize());
	}

	public File relativize(File other) {
		return File(other.path.relativize(other.path));
	}

	public File resolve(File other) {
		return File(path.resolve(other.path));
	}

	public File resolve(String other) {
		return File(path.resolve(other));
	}

	public boolean startsWith(File other) {
		return path.startsWith(other.path);
	}

	public File subpath(int beginIndex, int endIndex) {
		return File(path.subpath(beginIndex, endIndex));
	}

	public boolean changeParent(File parent) {
		if (!parent.isDirectory() || getNameCount() == 0) {
			return false;
		}
		return move(parent.resolve(getName()));
	}

	public String getExtension() {
		String filename = getName();
		int lastDot = filename.lastIndexOf(".");
		if (lastDot == -1 || filename.length() < lastDot + 1) {
			return "";
		}
		return filename.substring(lastDot + 1);
	}

	@Override
	public int compareTo(File pathname) {
		return file.compareTo(pathname.file);
	}
}
