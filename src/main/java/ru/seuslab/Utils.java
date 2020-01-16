package ru.seuslab;

import com.google.gson.Gson;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List unZip(String str) throws IOException {
        ArrayList<Group> list = new ArrayList<>();
        //получаем входной поток с файла с оберткой в обработчики
        FileInputStream in = new FileInputStream(str);
        BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
        TarArchiveInputStream tarIn = new TarArchiveInputStream(bzIn);

        ArchiveEntry entry = null;

        //последовательно считываем файлы из архива, и парсим в list
        while ((entry = tarIn.getNextEntry()) != null) {
            if (entry.getSize() < 1) {
                continue;
            }
            byte[] buf = new byte[(int) entry.getSize()];
            int k = tarIn.read(buf, 0, buf.length);
            String s = new String(buf, 0, k);
            //парсим из Json в объект Group
            Gson g = new Gson();
            Group group = g.fromJson(s, Group.class);

            list.add(group);
        }
        tarIn.close();
        return list;
    }
}
