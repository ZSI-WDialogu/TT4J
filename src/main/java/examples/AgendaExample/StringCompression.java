package examples.AgendaExample;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

/**
 * Created by Stokowiec on 2015-07-01.
 */
public class StringCompression {

    public static String decompress(byte[] bytes) throws Exception {

        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        String outStr = "";
        String line;
        while ((line=bf.readLine())!=null) {
            outStr += line;
        }
        return outStr;
    }

    public static byte[] compress(String text) throws Exception {
        if (text == null || text.length() == 0) {
            return new byte[]{};
        }
        byte[] buffer = text.getBytes("UTF-8");

        return getBytes(buffer);
    }

    public static String compressToStringCSharp(String text) throws Exception {

        byte[] buffer = text.getBytes("UTF-8");
        byte[] compressedData = getBytes(buffer);
        byte[] gZipBuffer = new byte[compressedData.length + 4];

        System.arraycopy(compressedData, 0, gZipBuffer, 4, compressedData.length);
        System.arraycopy(getBytes(buffer.length), 0, gZipBuffer, 0, 4);

        return Base64.encodeBase64String(gZipBuffer);
    }


    public static String decompressFromCSharp(String compressedText) throws Exception {
        byte[] compressed = compressedText.getBytes("UTF8");
        compressed = org.apache.commons.codec.binary.Base64.decodeBase64(compressed);
        byte[] buffer=new byte[compressed.length-4];
        buffer = copyForDecompression(compressed, buffer, 4, 0);

        final int BUFFER_SIZE = 32;
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        GZIPInputStream gis = new GZIPInputStream(is, BUFFER_SIZE);
        StringBuilder string = new StringBuilder();
        byte[] data = new byte[BUFFER_SIZE];
        int bytesRead;

        while ((bytesRead = gis.read(data)) != -1)
        {
            string.append(new String(data, 0, bytesRead));
        }

        gis.close();
        is.close();
        return string.toString();
    }




    private static  byte[] copyForDecompression(byte[] b1 ,byte[] b2, int srcoffset, int dstoffset)
    {
        for(int i=0;i<b2.length && i<b1.length;i++)
        {
            b2[i]=b1[i+4];
        }
        return b2;
    }

    private static byte[] getBytes(int value) {
        ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(value);
        return buffer.array();
    }

    private static byte[] getBytes(byte[] buffer) throws IOException {
        ByteArrayOutputStream obj=new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(obj);
        gzip.write(buffer);
        gzip.close();

        return obj.toByteArray();
    }
}
