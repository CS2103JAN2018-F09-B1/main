
/**
 * Checks if the two given files are binary equivalent.
 * @param file1 is a file on harddisk
 * @param file2 is a different file from @code file1 on the harddisk
 * @return whether the two files given are equal
 * @throws IOException when there is an issue reading from either file
 */
//@@author Alaru-unused
//Unused due to other teammate's feature breaking the original intention, originally from FileUtil
public static boolean isSameFile(File file1, File file2) throws IOException {
    if (file1.length() != file2.length()) {
        return false;
    }

    BufferedInputStream bisO = new BufferedInputStream(new FileInputStream(file1));
    BufferedInputStream bisN = new BufferedInputStream(new FileInputStream(file2));
    byte[] bufferO = new byte[4096];
    byte[] bufferN = new byte[4096];
    int fileBytes1 = bisO.read(bufferO);
    bisN.read(bufferN);
    while (fileBytes1 != -1) {
        if (!Arrays.equals(bufferO, bufferN)) {
            bisO.close();
            bisN.close();
            return false;
        }
        fileBytes1 = bisO.read(bufferO);
        bisN.read(bufferN);
    }
    bisO.close();
    bisN.close();
    return true;
}
