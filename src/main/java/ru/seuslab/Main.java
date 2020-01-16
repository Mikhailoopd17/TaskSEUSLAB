package ru.seuslab;
import java.io.IOException;
import java.util.*;

import static ru.seuslab.Utils.unZip;


public class Main {
    public static void main(String[] args) throws IOException {
        Map<Integer, Integer> result = new TreeMap<>();
        List<Group> list = unZip("junior2.tar.bz2"); //имя файла

        //поледовательно заполняем Map, в случае уже имеющегося key, увеличиваем value на 1
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).uids.size(); j++) {
                Integer a = list.get(i).uids.get(j);
                if (!result.containsKey(a)) {
                    result.put(a, 1);
                } else {
                    result.replace(a, result.get(a) + 1);
                }
            }
        }
        list.clear(); // большой объем list -> чистим принудительно (можно вынести в отдельный метод)

        //задаем начальные id и value для поиска максимального
        int id = 0;
        int value = 0;
        //перебором находим наибольшее значение
        for (Map.Entry e:result.entrySet()) {
            if((int)e.getValue() > value) {
                id = (int) e.getKey();
                value = (int) e.getValue();
            }
        }
        //выводим в консоль id участника состоящего в наибольшем колличестве групп
        System.out.println("Участник №"+id+" записан в наибольшее количество групп ("+value+"шт)");
    }

}
