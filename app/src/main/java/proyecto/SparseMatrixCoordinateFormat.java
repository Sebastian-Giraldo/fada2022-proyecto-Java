package proyecto;

import javax.naming.OperationNotSupportedException;
import lombok.Getter;
import lombok.Setter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.io.FileNotFoundException;

public class SparseMatrixCoordinateFormat {

    private LoadFile loader = LoadFile.getInstance();
    @Setter
    private int[][] matrix;
    @Getter
    @Setter
    private int[] rows;
    @Getter
    @Setter
    private int[] columns;
    @Getter
    @Setter
    private int[] values;

    public void matrizCoordenada() {
        List<Integer> rows = new ArrayList<>();
        List<Integer> columns = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    rows.add(i);
                    columns.add(j);
                    values.add(matrix[i][j]);
                }
            }
        }

        this.columns = new int[columns.size()];
        this.rows = new int[rows.size()];
        this.values = new int[values.size()];

        for (int i = 0; i < values.size(); i++) {
            this.values[i] = values.get(i);
            this.rows[i] = rows.get(i);
            this.columns[i] = columns.get(i);
        }
    }

    public void createRepresentation(String inputFile) throws OperationNotSupportedException, FileNotFoundException {
        // Load data
        loader.loadFile(inputFile);
        matrix = loader.getMatrix();

        matrizCoordenada();

    }

    public int getElement(int i, int j) throws OperationNotSupportedException {
        for (int p = 0; p < this.values.length; p++) {
            if (this.rows[p] == i && this.columns[p] == j) {
                return this.values[p];
            }
        }
        return 0;
    }

    public int[] getRow(int i) throws OperationNotSupportedException {
        /*
         * Esta parte del código encuentra el máximo valor de columns.
         * La variable maxColumn se inicializa a 0 y luego se actualiza en cada
         * iteración del bucle
         * for con el máximo valor entre maxColumn y col. Al final del bucle, maxColumn
         * contendrá el
         * máximo valor de columns.
         */
        int maxColumn = 0;
        for (int col : columns) {
            maxColumn = Math.max(maxColumn, col);
        }
        /*
         * Aquí se crea un nuevo array de enteros llamado result con un tamaño igual al
         * máximo valor
         * de columns más 1. Esto se hace porque los índices de los arrays en Java
         * comienzan en 0, por
         * lo que el array result necesita tener un elemento adicional para almacenar el
         * valor máximo
         * de columns.
         */
        int[] result = new int[maxColumn + 1];
        /*
         * En este bucle se recorre el array rows y se compara cada elemento con el
         * valor de la
         * variable i. Si el elemento actual de rows es igual a i, se obtiene el valor
         * correspondiente
         * de columns y se asigna a la variable col. Luego se asigna el valor
         * correspondiente de values
         * a la posición del array result con el índice col. Esto es equivalente a
         * asignar el valor de
         * values[j] a la posición del array result con el índice columns[j].
         */
        for (int j = 0; j < rows.length; j++) {
            if (rows[j] == i) {
                int col = columns[j];
                result[col] = values[j];
            }
        }

        System.out.println(Arrays.toString(result));
        return result;
    }

    public int[] getColumn(int j) throws OperationNotSupportedException {
        int maxRow = 0;
        for (int row : rows) {
            maxRow = Math.max(maxRow, row);
        }
        int[] result = new int[maxRow + 1];

        for (int k = 0; k < columns.length; k++) {
            if (columns[k] == j) {
                int row = rows[k];
                result[row] = values[k];
            }
        }

        System.out.println(Arrays.toString(result));
        return result;
    }

    public void setValue(int i, int j, int value) throws OperationNotSupportedException {
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix.length; column++) {
                if (row == i && column == j) {
                    matrix[row][column] = value;
                }
            }
        }
        matrizCoordenada();
    }

    /*
     * This method returns a representation of the Squared matrix
     * @return object that contests the squared matrix;
     */
    public SparseMatrixCoordinateFormat getSquareMatrix() throws OperationNotSupportedException {
        SparseMatrixCoordinateFormat squaredMatrix = new SparseMatrixCoordinateFormat();
        squaredMatrix.setRows(getRows());
        squaredMatrix.setColumns(getColumns());
        int[] resultado = new int[getValues().length];
        for (int i = 0; i < getValues().length; i++) {
            resultado[i] = getValues()[i] * getValues()[i];
        }
        squaredMatrix.setValues(resultado);
        return squaredMatrix;
    }

    /*
     * This method returns a representation of the transposed matrix
     * @return object that contests the transposed matrix;
     */
    public SparseMatrixCoordinateFormat getTransposedMatrix() throws OperationNotSupportedException {
        SparseMatrixCoordinateFormat squaredMatrix = new SparseMatrixCoordinateFormat();

        // Creamos la nueva matriz transpuesta
        int[][] transposedMatrix = new int[matrix[0].length][matrix.length];

        // Transponemos la matriz original
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                transposedMatrix[j][i] = matrix[i][j];
            }
        }

        // Contamos cuántos elementos distintos de cero hay en la nueva matriz
        // transpuesta
        int nonZeroElements = 0;
        for (int[] row : transposedMatrix) {
            for (int element : row) {
                if (element != 0) {
                    nonZeroElements++;
                }
            }
        }

        // Creamos los vectores de filas, columnas y valores de la matriz dispersa
        int[] rows = new int[nonZeroElements];
        int[] columns = new int[nonZeroElements];
        int[] values = new int[nonZeroElements];

        // Rellenamos los vectores con los datos de la nueva matriz transpuesta
        int index = 0;
        for (int i = 0; i < transposedMatrix.length; i++) {
            for (int j = 0; j < transposedMatrix[0].length; j++) {
                if (transposedMatrix[i][j] != 0) {
                    rows[index] = i;
                    columns[index] = j;
                    values[index] = transposedMatrix[i][j];
                    index++;
                }
            }
        }

        // Establecemos los vectores de filas, columnas y valores en la matriz dispersa
        squaredMatrix.setRows(rows);
        squaredMatrix.setColumns(columns);
        squaredMatrix.setValues(values);

        return squaredMatrix;
    }

}
