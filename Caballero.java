package caballero;

import java.util.*;

/**
 *
 * @author Juane
 */
public class Caballero {
    private final static int multiplicador = 8;
    private final static int lado = multiplicador + 4;
    private final static int[][] movimientos = {{1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}};
    private static int[][] grilla;
    private static double total;

    public static void main(String[] args) {
        grilla = new int[lado][lado];
        total = Math.pow(multiplicador, 2);
        for (int i = 0; i < lado; i++) {
            for (int j = 0; j < lado; j++) {
                if (i < 2 || i > multiplicador + 1 || j < 2 || j > multiplicador + 1) {
                    grilla[i][j] = -1;
                }
            }
        }        

        int fila = 2 + (int) (Math.random() * multiplicador);
        int columna = 2 + (int) (Math.random() * multiplicador);

        grilla[fila][columna] = 1;

        if (buscar(fila, columna, 2)) {
            imprimir();
        } else {
            System.out.println("Sin resultado");
        }
    }

    private static boolean buscar(int fila, int columna, int cuenta) {

        if (cuenta > total) {
            return true;
        }

        List<int[]> vecinos = vecinos(fila, columna);

        if (vecinos.isEmpty() && cuenta != total) {
            return false;
        }

        Collections.sort(vecinos, new Comparator<int[]>() {
            @Override
            public int compare(int[] x, int[] y) {
                return x[2] - y[2];
            }
        });

        for (int[] vecino : vecinos) {
            fila = vecino[0];
            columna = vecino[1];

            grilla[fila][columna] = cuenta;

            if (!elemHuerfano(cuenta, fila, columna) && buscar(fila, columna, cuenta + 1)) {
                return true;
            }

            grilla[fila][columna] = 0;
        }
        return false;
    }

    private static List<int[]> vecinos(int fila, int columna) {
        List<int[]> vecinos = new ArrayList<>();

        for (int[] k : movimientos) {
            int a = k[0];
            int b = k[1];
            if (grilla[fila + a][columna + b] == 0) {
                int num = contarVecinos(fila + a, columna + b);
                vecinos.add(new int[]{fila + a, columna + b, num});
            }
        }
        return vecinos;
    }

    private static int contarVecinos(int fila, int columna) {
        int num = 0;
        for (int[] l : movimientos) {
            
            if (grilla[fila + l[1]][columna + l[0]] == 0) {
                num++;
            }
            
        }
        return num;
    }

    private static boolean elemHuerfano(int cuenta, int fila, int columna) {
        
        if (cuenta < total - 1) {
            List<int[]> vecinos = vecinos(fila, columna);
            for (int[] vecino : vecinos) {
                if (contarVecinos(vecino[0], vecino[1]) == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    private static void imprimir() {
        for (int[] fila : grilla) {
            for (int m : fila) {
                
                if (m == -1) {
                    continue;
                }
  
                System.out.printf("%2d ", m);
            }

            System.out.println();
        }
    }
}
