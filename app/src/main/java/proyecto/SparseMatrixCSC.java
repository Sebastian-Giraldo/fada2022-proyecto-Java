package proyecto;

import javax.naming.OperationNotSupportedException;
import lombok.Getter;
import java.io.FileNotFoundException;

public class SparseMatrixCSC {
    private LoadFile loader = LoadFile.getInstance();
    private int[][] matrix;
    @Getter
    private int[] rows;
    @Getter
    private int[] columns;

    public void setValues(int[] values) {
        this.values = values;
    }

    @Getter
    private int[] values;

    public void matrizCSC() {
        int contador = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    contador++;
                }
            }
        }

        values = new int[contador];
        rows = new int[contador];
        columns = new int[matrix[0].length + 1];
        contador = 0;

        for (int j = 0; j < matrix[0].length; j++) { // Columnas
            for (int i = 0; i < matrix.length; i++) { // Filas
                if (matrix[i][j] != 0) {
                    values[contador] = matrix[i][j];
                    rows[contador] = i;
                    contador++;
                }
            }
            columns[j + 1] = contador;
        }
    }

    public void createRepresentation(String inputFile) throws OperationNotSupportedException, FileNotFoundException {
        // Load data
        loader.loadFile(inputFile);
        matrix = loader.getMatrix();

        matrizCSC();
    }

    public int getElement(int i, int j) throws OperationNotSupportedException {
        /*
         * El bucle for itera sobre las columnas de la matriz. En cada iteración,
         * se compara el valor de la columna actual con el valor de la columna
         * siguiente. Si son diferentes, significa que hay elementos no nulos en la
         * columna actual, por lo que se procede a buscar el elemento deseado en esa
         * columna.
         */

        if (columns[j] != columns[j + 1]) {
            for (int row = columns[j]; row < columns[j + 1]; row++) {
                if (rows[row] == i) {
                    return values[row];
                }
            }
        }
        return 0;
    }

    public int[] getRow(int i) throws OperationNotSupportedException {
        // Creamos un array para almacenar los valores de la fila buscada
        int[] row = new int[columns.length - 1];

        // Buscamos si la fila existe en la matriz
        boolean rowExists = false;
        for (int r : rows) {
            if (r == i) {
                rowExists = true;
                break;
            }
        }

        // Si la fila existe, rellenamos el array con los valores de la fila
        if (rowExists) {
            for (int j = 0; j < row.length; j++) {
                // Si hay elementos no nulos en la columna j, buscamos el elemento en la fila i
                if (columns[j] != columns[j + 1]) {
                    for (int r = columns[j]; r < columns[j + 1]; r++) {
                        if (rows[r] == i) {
                            row[j] = values[r];
                        }
                    }
                }
            }
        }

        // Retornamos el array con los valores de la fila
        return row;

    }

    public int[] getColumn(int j) throws OperationNotSupportedException {
        // Creamos un array para almacenar los valores de la columna buscada
        int[] column = new int[matrix.length];

        // Si hay elementos no nulos en la columna j, rellenamos el array con los
        // valores de la columna
        if (columns[j] != columns[j + 1]) {
            for (int row = columns[j]; row < columns[j + 1]; row++) {
                column[rows[row]] = values[row];
            }
        }

        // Retornamos el array con los valores de la columna
        return column;

    }

    public void setValue(int i, int j, int value) throws OperationNotSupportedException {
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix.length; column++) {
                if (row == i && column == j) {
                    matrix[row][column] = value;
                }
            }
        }
        matrizCSC();
    }

    public LoadFile getLoader() {
        return loader;
    }

    public void setLoader(LoadFile loader) {
        this.loader = loader;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[] getRows() {
        return rows;
    }

    public int[] getColumns() {
        return columns;
    }

    public void setColumns(int[] columns) {
        this.columns = columns;
    }

    public int[] getValues() {
        return values;
    }

    /*
     *  This method returns a representation of the Squared matrix
     *  @return object that contests the squared matrix;
     */
    public SparseMatrixCSC getSquareMatrix() throws OperationNotSupportedException {
        SparseMatrixCSC squaredMatrix = new SparseMatrixCSC();
        squaredMatrix.setRows(getRows());
        squaredMatrix.setColumns(getColumns());

        int[] resultado = new int[getValues().length];

        for (int i = 0; i < getValues().length; i++) {
            resultado[i] = getValues()[i] * getValues()[i];
        }
        squaredMatrix.setValues(resultado);

        return squaredMatrix;
    }

    public void setRows(int[] rows) {
        this.rows = rows;
    }

    /*
     *  This method returns a representation of the transposed matrix
     *  @return object that contests the transposed matrix;
     */
    public SparseMatrixCSC getTransposedMatrix() throws OperationNotSupportedException {

        SparseMatrixCSC squaredMatrix = new SparseMatrixCSC();
        int resultado[][] = new int[matrix[0].length][matrix.length];
        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                resultado[j][i] = matrix[i][j];
            }
        }

        squaredMatrix.setMatrix(resultado);
        int counter = 0;

        for (int i = 0; i < resultado.length; i++) {
            for (int j = 0; j < resultado[0].length; j++) {
                if (resultado[i][j] != 0) {
                    counter++;
                }
            }
        }
        int[] nuevasFilas = new int[counter];
        int[] nuevosValores = new int[counter];
        int[] nuevasColumnas = new int[resultado[0].length + 1];
        counter = 0;

        for (int j = 0; j < resultado[0].length; j++) { // Columns
            for (int i = 0; i < resultado.length; i++) { // Rows
                if (resultado[i][j] != 0) {
                    nuevosValores[counter] = resultado[i][j];
                    nuevasFilas[counter] = i;
                    counter++;
                }
            }
            nuevasColumnas[j + 1] = counter;
        }
        squaredMatrix.setValues(nuevosValores);
        squaredMatrix.setRows(nuevasFilas);
        squaredMatrix.setColumns(nuevasColumnas);
        return squaredMatrix;
    }
}
