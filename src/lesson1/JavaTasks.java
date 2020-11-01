package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     * <p>
     * Пример:
     * <p>
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     * ----------
     * Трудоемкость O(nlog(n))
     * Ресурсоемкость O(n)
     * ----------
     */
    //
    static public void sortAddresses(String inputName, String outputName) {
        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(new FileInputStream(inputName),
                StandardCharsets.UTF_8));
             FileWriter writer = new FileWriter(new File(outputName), StandardCharsets.UTF_8)) {
            Map<String, TreeSet<String>> addressOfPeople =
                    new TreeMap<>((o1, o2) -> {
                        String[] O1 = o1.split("\\s");
                        String[] O2 = o2.split("\\s");
                        if (O1[0].compareTo(O2[0]) == 0) {
                            return Integer.compare(Integer.parseInt(O1[1]), Integer.parseInt(O2[1]));
                        } else
                            return O1[0].compareTo(O2[0]);
                    });
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parsedLine;
                parsedLine = line.split("\\s-\\s", 2);
                TreeSet<String> name = addressOfPeople.getOrDefault(parsedLine[1], new TreeSet<>());
                name.add(parsedLine[0]);
                addressOfPeople.put(parsedLine[1], name);
            }
            for (Entry<String, TreeSet<String>> entry : addressOfPeople.entrySet()) {
                StringBuilder string = new StringBuilder();
                string.append(entry.getKey());
                string.append(" - ");
                string.append(entry.getValue().toString(), 1, entry.getValue().toString().length() - 1);
                string.append("\n");
                writer.write(string.toString());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     * ----------
     * Трудоемкость O(n + k)
     * Ресурсоемкость O(n)
     * ----------
     */
    static public void sortTemperatures(String inputName, String outputName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputName));
             FileWriter writer = new FileWriter(outputName)) {
            ArrayList<Integer> temp = new ArrayList<>();
            String number;
            while ((number = reader.readLine()) != null) {
                int intNum = Integer.parseInt(number.replace(".", ""));
                intNum += 2730;
                temp.add(intNum);
            }
            int[] arr = temp.stream().mapToInt(Integer::intValue).toArray();
            arr = Sorts.countingSort(arr, 7730);
            for (double i : arr) {
                i -= 2730;
                i /= 10;
                writer.write(String.valueOf(i));
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new NotImplementedError();
        }
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     * ----------
     * Трудоемкость O(n)
     * Ресурсоемкость O(n)
     * ----------
     */
    static public void sortSequence(String inputName, String outputName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputName));
             FileWriter writer = new FileWriter(outputName)) {
            String number;
            List<Integer> init = new ArrayList<>();
            Map <Integer, Integer> map = new TreeMap<>();
            while ((number = reader.readLine()) != null) {
                init.add(Integer.parseInt(number));
                int count = map.getOrDefault(Integer.parseInt(number), 0);
                count++;
                map.put(Integer.parseInt(number), count);
            }
            int maxNum = 0, maxCount = 0;
            for(Entry <Integer, Integer> entry : map.entrySet()){
                if(entry.getValue() > maxCount || (entry.getValue() == maxCount && entry.getKey() < maxNum)){
                    maxCount = entry.getValue();
                    maxNum = entry.getKey();
                }
            }
            for (Integer integer : init) {
                if (integer != maxNum)
                    writer.write(integer + "\n");
            }
            for (int j = 0; j < maxCount; j++) {
                writer.write(maxNum + "\n");
            }
        } catch (IOException e) {
            throw new NotImplementedError();
        }
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
