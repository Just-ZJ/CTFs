import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public final class ProbA {

    private static void turn(LinkedList<Integer> order, int currentStudentPos,
            boolean oddTurn, boolean oddStudents) {
        int maxPos = order.size() - 1;

        if (currentStudentPos + 1 > maxPos && !oddTurn) {
            //swap first with last(looping)
            Collections.swap(order, currentStudentPos, 0);
        } else {
            //normal swap. not first or last
            Collections.swap(order, currentStudentPos, currentStudentPos + 1);
        }

        if (oddStudents) {
            if (currentStudentPos + 2 < maxPos) {
                turn(order, currentStudentPos + 2, oddTurn, oddStudents);
            }
        } else {
            if (currentStudentPos + 2 <= maxPos) {
                turn(order, currentStudentPos + 2, oddTurn, oddStudents);
            }
        }

    }

    private static void oddOrEvenTurn(LinkedList<Integer> order,
            int currentTurn, int totalTurn, boolean oddStudents) {
        boolean oddTurn = false;
        if (currentTurn <= totalTurn) {
            if (currentTurn % 2 == 0) {
                //even turn
                turn(order, 1, oddTurn, oddStudents);
            } else {
                //odd turn
                oddTurn = true;
                turn(order, 0, oddTurn, oddStudents);
                oddTurn = false;
            }
            oddOrEvenTurn(order, currentTurn + 1, totalTurn, oddStudents);
        }

    }

    private static int[] getLeftRightStudent(LinkedList<Integer> order, int M) {
        int[] leftRight = new int[2];
        int numOfStudents = order.size(); //starts at 0
        int studentMpos = order.indexOf(M);

        if (studentMpos == 0) { //M-1 is student M position
            leftRight[1] = order.get(numOfStudents - 1); //last student
        } else {
            leftRight[1] = order.get(studentMpos - 1);
        }

        if (studentMpos == numOfStudents - 1) {
            leftRight[0] = order.get(0); //first student
        } else {
            leftRight[0] = order.get(studentMpos + 1);
        }

        return leftRight;
    }

    private static void createOrder(LinkedList<Integer> order, int numOfStudent,
            int current) {
        if (current <= numOfStudent) {
            order.add(current);
            createOrder(order, numOfStudent, current + 1);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int numOfStudent = in.nextInt(), totalTurn = in.nextInt(),
                M = in.nextInt();

        if (numOfStudent >= 3 && numOfStudent <= 10000 && totalTurn > 0
                && totalTurn < 10000 && M > 0 && M < 10000) {
            boolean oddStudents = false;
            if (numOfStudent % 2 != 0) {
                oddStudents = true;
            }
            LinkedList<Integer> order = new LinkedList<Integer>();
            createOrder(order, numOfStudent, 1);
            oddOrEvenTurn(order, 1, totalTurn, oddStudents);
            int[] leftRight = getLeftRightStudent(order, M);
            System.out.print(leftRight[0] + " " + leftRight[1]);
        }
        in.close();
    }
}
