package proyecto;

import javax.naming.OperationNotSupportedException;
import lombok.Getter;
import java.io.FileNotFoundException;

public class SparseMatrixCSR {
    private LoadFile loader = LoadFile.getInstance();
    private int[][] matrix;

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

    public int[] getValues() {
        return values;
    }

    @Getter
    private int[] rows;
    @Getter
    private int[] columns;
    @Getter
    private int[] values;

    public void matrizCSR() {

        int contador = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    contador++;
                }
            }
        }

        columns = new int[contador];
        values = new int[contador];
        rows = new int[matrix.length + 1];
        contador = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    values[contador] = matrix[i][j];
                    columns[contador] = j;
                    contador++;
                }
            }
            rows[i + 1] = contador;
        }
    }

    public void createRepresentation(String inputFile) throws OperationNotSupportedException, FileNotFoundException {
        // Load data
        loader.loadFile(inputFile);
        matrix = loader.getMatrix();

        matrizCSR();
    }

    public int getElement(int i, int j) throws OperationNotSupportedException {
        int buscador = rows[i];
        for (int k = buscador; k < rows[i + 1]; k++) {
            if (columns[k] == j) {
                return values[k];
            }
        }
        return 0;

    }

    public int[] getRow(int i) throws OperationNotSupportedException {
        // Creamos un array para almacenar los valores de la fila buscada
        int[] row = new int[matrix[2].length];

        // Obtenemos el índice del primer elemento no nulo de la fila
        int index = rows[i];

        // Recorremos los elementos no nulos de la fila
        while (index < rows[i + 1]) {
            // Almacenamos el elemento en la posición correspondiente del array
            row[columns[index]] = values[index];

            // Pasamos al siguiente elemento no nulo
            index++;
        }

        // Retornamos el array con los valores de la fila
        return row;

    }

    public int[] getColumn(int j) throws OperationNotSupportedException {
        // Creamos un array para almacenar los valores de la columna buscada
        int[] column = new int[rows.length - 1];

        // Recorremos las filas de la matriz dispersa
        for (int i = 0; i < rows.length - 1; i++) {
            // Recorremos los elementos no nulos de la fila
            for (int k = rows[i]; k < rows[i + 1]; k++) {
                // Si el elemento está en la columna buscada, lo almacenamos en el array
                if (columns[k] == j) {
                    column[i] = values[k];
                }
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
        matrizCSR();

    }

    /*
     * This method returns a representation of the Squared matrix
     * 
     * @return object that contests the squared matrix;
     */
    public SparseMatrixCSR getSquareMatrix() throws OperationNotSupportedException {
        SparseMatrixCSR squaredMatrix = new SparseMatrixCSR();

        squaredMatrix.setRows(getRows());
        squaredMatrix.setColumns(getColumns());

        int[] newValores = new int[getValues().length];

        for (int i = 0; i < getValues().length; i++) {
            newValores[i] = getValues()[i] * getValues()[i];
        }
        squaredMatrix.setValues(newValores);

        return squaredMatrix;
    }

    public void setRows(int[] rows) {
        this.rows = rows;
    }

    public void setColumns(int[] columns) {
        this.columns = columns;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    /*
     * This method returns a representation of the transposed matrix
     * 
     * @return object that contests the transposed matrix;
     */
    public SparseMatrixCSR getTransposedMatrix() throws OperationNotSupportedException {
        SparseMatrixCSR squaredMatrix = new SparseMatrixCSR();
        int[][] resultado = new int[matrix[0].length][matrix.length];

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

        int[] nuevosValores = new int[counter];
        int[] nuevasfilas = new int[resultado.length + 1];
        int[] nuevasColumnas = new int[counter];

        counter = 0;

        for (int i = 0; i < resultado.length; i++) {
            for (int j = 0; j < resultado[0].length; j++) {
                if (resultado[i][j] != 0) {
                    nuevosValores[counter] = resultado[i][j];
                    nuevasColumnas[counter] = j;
                    counter++;
                }
            }
            nuevasfilas[i + 1] = counter;
        }
        squaredMatrix.setColumns(nuevasColumnas);
        squaredMatrix.setValues(nuevosValores);
        squaredMatrix.setRows(nuevasfilas);
        return squaredMatrix;
    }

}
