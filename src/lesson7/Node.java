package lesson7;

import lombok.Getter;

import java.util.Objects;

import static java.lang.Math.abs;

public class Node {
    @Getter
    public Pair<Integer, Integer> position;
    @Getter
    public Pair<Integer, Integer> finish;
    public Node parent;
    @Getter
    public int F;// F=G+H
    @Getter
    public int G; // расстояние от старта до ноды
    public int H; // расстояние от ноды до цели

    public Node(int g, Pair<Integer, Integer> nodePosition, Pair<Integer, Integer> targetPosition, Node previousNode) {
        position = nodePosition;
        finish = targetPosition;
        parent = previousNode;
        G = g;
        H = Math.max(abs(targetPosition.getFirst() - position.getFirst())
                ,abs(targetPosition.getSecond() - position.getSecond()));
        F = G + H;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return F == node.F &&
                G == node.G &&
                H == node.H &&
                Objects.equals(position, node.position) &&
                Objects.equals(finish, node.finish) &&
                Objects.equals(parent, node.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, finish, parent, F, G, H);
    }
}