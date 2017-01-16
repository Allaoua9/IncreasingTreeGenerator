package TreeGenerator;

import Tree.Node;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * BooXchange Project
 * Created by Al on 14/01/2017.
 */
public class IncreasingTreeGenerator {

    public ArrayList<BigInteger> coefficients = new ArrayList<>();

    public IncreasingTreeGenerator() {
        this.coefficients.add(BigInteger.ONE);
    }

    public Node generateTree(int size, BigInteger order) throws Exception {

        processCoeffiecients(size);

        if (size <= 0) {
            return null;
        }
        else if (size == 1) {
            Node node = new Node();
            node.setWeight(1);
            node.setParent(null);
            return node;
        }
        else {

            Node node = new Node();
            node.setWeight(size);
            node.setChildren(generateForest(size-1, order));

            return node;
        }
    }

    private ArrayList<Node> generateForest(int size, BigInteger order) throws Exception {

        if (size <= 0) {
            return new ArrayList<>();
        }
        else {
            this.processCoeffiecients(size);

            if (order.compareTo(this.coefficients.get(size)) >= 0) {
                System.out.println("ERROR : order must be less than (" + this.coefficients.get(size) + ")");
                throw new Exception("ERROR : order must be less than (" + this.coefficients.get(size) + ")");
            }
            else {

                BigInteger coef;
                int i = 1;
                boolean orderPositive = true;
                while (orderPositive) {
                    coef = combination(size, i)
                            .multiply(this.coefficients.get(i-1).multiply(this.coefficients.get(size - i)));

                    orderPositive = (order.subtract(coef)).compareTo(BigInteger.ZERO) >= 0;
                    if (orderPositive)  {
                        i++;
                        order = order.subtract(coef);
                    }
                }

                System.out.println("INFO : order = " + order + ", at i = " + i);

                BigInteger order1 = order.divide(combination(size, i)).mod(this.coefficients.get(i-1));
                Node nodeG = generateTree(i, order1);

                ArrayList<Node> nodes = new ArrayList<>();
                nodes.add(nodeG);

                BigInteger order2 = order.divide(combination(size, i)).divide(this.coefficients.get(i-1))
                        .mod(this.coefficients.get(size - i));
                nodes.addAll( generateForest(size - i, order2));

                return nodes;
            }
        }
    }

    public void processCoeffiecients(int size) {

        int i = coefficients.size() - 1;

        if (i < size) {
            System.out.println("INFO : Processing Coefficients...");
            while (i < size) {
                BigInteger sum = BigInteger.ZERO;
                System.out.println("***" + (i+1));
                for (int j = 1; j <= i + 1; j++) {

                    sum = sum.add(coefficients.get(j - 1).multiply(coefficients.get(i + 1 - j))
                            .multiply(combination(i+1, j)));
                }
                coefficients.add(sum);
                i++;
            }
            System.out.println("//");
            System.out.println("INFO : Coefficients Processed.");
        }
        else {
            System.out.println("INFO : Coefficients Cached...");
        }
    }

    public Node labelThis(Node node) {


        ArrayList<Node> candidats = new ArrayList<>();
        candidats.add(node);
        int summedWeight = node.getWeight();
        int label = 1;
        while(!candidats.isEmpty()) {
            Node current = selectNextNode(candidats, summedWeight);
            current.setValue(label++);

            candidats.remove(current);
            candidats.addAll(current.getChildren());
            summedWeight -= current.getWeight();
            for(Node child: current.getChildren() ) {
                summedWeight += child.getWeight();
            }
        }

        return node;
    }

    private Node selectNextNode(ArrayList<Node> candidats, int summedWeight) {
        Random random = new Random();
        int res = random.nextInt(summedWeight);

        for(Node child : candidats) {
            res -= child.getWeight();
            if(res < 0) return child;
        }
        System.out.println("Allaoua was worried !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return null;
    }

    public BigInteger combination(int n, int k) {

        BigInteger nfact = factorial(n).divide(factorial(k).multiply(factorial(n-k)));

        return nfact;
    }

    public BigInteger factorial(int n) {

        BigInteger res = BigInteger.ONE;

        for (int i = 1; i <= n; i++) {
            res = res.multiply(BigInteger.valueOf(i)) ;
        }

        return res;
    }


}

