package lesson7;

import kotlin.NotImplementedError;


import java.io.*;
import java.util.*;

import static java.lang.Math.max;


@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        int n = first.length() + 1, m = second.length() + 1, i, j;
        int[][] matrix = new int[n][m];
        StringBuilder ans = new StringBuilder();
        for (i = 1; i < n; i++) {
            for (j = 1; j < m; j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1))
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                else
                    matrix[i][j] = max(matrix[i - 1][j], matrix[i][j - 1]);
            }
        }
        i = --n;
        j = --m;
        while (i > 0 && j > 0) {
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                ans.append(first.charAt(i - 1));
                --i;
                --j;
            } else if (matrix[i - 1][j] == matrix[i][j]) --i;
            else --j;
        }
        return ans.reverse().toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        throw new NotImplementedError();
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) throws IOException { // remember idea to use A*
        try (BufferedReader reader = new BufferedReader(new FileReader(inputName))) {
            ArrayList<String> matrix = new ArrayList<>();
            Set<Node> closed = new HashSet<>();
            while (reader.ready()) matrix.add(reader.readLine().replaceAll(" ", ""));
            Queue<Node> open = new PriorityQueue<>(Comparator.comparing(Node::getF));
            Pair<Integer, Integer> startPosition = new Pair<>(0, 0);
            Pair<Integer, Integer> targetPosition = new Pair<>(matrix.get(0).length() - 1, matrix.size() - 1);
            if (startPosition == targetPosition) return 0;
            Node startNode = new Node(0, startPosition, targetPosition, null);
            closed.add(startNode);
            open.addAll(getNeighbours(startNode, matrix, closed));
            while (!open.isEmpty()) {
                Node nodeToCheck = open.poll();
                if (nodeToCheck.getPosition().getFirst().equals(targetPosition.getFirst()) &&
                        nodeToCheck.getPosition().getSecond().equals(targetPosition.getSecond())) {
                    return nodeToCheck.getG();
                }
                closed.add(nodeToCheck);
                for (Node v : getNeighbours(nodeToCheck, matrix, closed)) {
                    if (!containsNode(closed, v)) {
                        open.add(v);
                    }
                }
            }
            return 0;
        }
    }

    private static Boolean containsNode(Set<Node> closed, Node node){
        for(Node v : closed){
            if (node.getPosition().getFirst().equals(v.getPosition().getFirst()) &&
                    node.getPosition().getSecond().equals(v.getPosition().getSecond())
            && node.getF() == node.getF()) return true;
        }
        return false;
    }

    public static List<Node> getNeighbours(Node node, ArrayList<String> matrix, Set<Node> closed) {
        List<Node> neighbours = new ArrayList<>();
        int x, y;
        int X = matrix.get(0).length(), Y = matrix.size();
        Node addable;
        x = node.getPosition().getFirst();
        y = node.getPosition().getSecond();
        if (x + 1 < X)
            neighbours.add(new Node(node.getG() + (matrix.get(y).charAt(x + 1) - '0'), new Pair<>(
                    x + 1, y),
                    node.getFinish(),
                    node));
        if (x + 1 < X && y + 1 < Y) {
            addable = new Node(node.getG() + (matrix.get(y + 1).charAt(x + 1) - '0'), new Pair<>(
                    x + 1, y + 1),
                    node.getFinish(),
                    node);
                neighbours.add(addable);
        }
        if (y + 1 < Y)
            neighbours.add(new Node(node.getG() + (matrix.get(y + 1).charAt(x) - '0'), new Pair<>(
                    x, y + 1),
                    node.getFinish(),
                    node));
        return neighbours;
    }


    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}