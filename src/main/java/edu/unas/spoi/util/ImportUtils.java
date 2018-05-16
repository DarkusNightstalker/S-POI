/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.Part;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The type Import utils.
 *
 * @author User
 */
public class ImportUtils {

    /**
     * The enum State.
     */
    public enum State {

        /**
         * The Load.
         */
        LOAD("Subiendo archivo ...", false),
        /**
         * The Reading.
         */
        READING("Validando el contenido ...", false),
        /**
         * The Process.
         */
        PROCESS("Guardando registros...", false),
        /**
         * Error state.
         */
        ERROR("FINALIZADO", true),
        /**
         * Success state.
         */
        SUCCESS("FINALIZADO", true);
        private final String description;
        private final boolean terminate;

        private State(String description, boolean terminate) {
            this.description = description;
            this.terminate = terminate;
        }

        /**
         * Gets description.
         *
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * Is terminate boolean.
         *
         * @return the boolean
         */
        public boolean isTerminate() {
            return terminate;
        }
    }

    /**
     * Read file object.
     *
     * @param file the file
     * @return the object
     * @throws IOException the io exception
     */
    public static Object readFile(Part file) throws IOException {
        String ct = file.getContentType();
        boolean isXML = true;
        Object o;
        if (isXML) {
            //Archivo XLSX
            o = new XSSFWorkbook(file.getInputStream());
        } else {
            //Archivo XLS
            o = new HSSFWorkbook(file.getInputStream());
        }
        return o;
    }

    /**
     * Count rows integer.
     *
     * @param file the file
     * @return the integer
     */
    public static Integer countRows(Object file) {
        Workbook workbook = (Workbook) file;
        return workbook.getSheetAt(0).getLastRowNum();
    }

    /**
     * Read row object [ ].
     *
     * @param file      the file
     * @param rowNumber the row number
     * @param columns   the columns
     * @return the object [ ]
     * @throws Exception the exception
     */
    public static Object[] readRow(Object file, int rowNumber, int columns) throws Exception {
        Workbook workbook = (Workbook) file;
        Row row = workbook.getSheetAt(0).getRow(rowNumber);
        Iterator<Cell> iterator = row.iterator();
        List<Object> data = new ArrayList();
        for (int i = 0; i < columns; i++) {
            Cell cell = row.getCell(i);
            if (cell == null) {
                data.add("");
                data.set(data.size() - 1, null);
                continue;
            }
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    data.add("");
                    data.set(data.size() - 1, null);
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    data.add(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    throw new Exception("Se ha encontrado un error en el contenido");
                case Cell.CELL_TYPE_FORMULA:
                    throw new Exception("Las formulas no estan soportadas en la importacion");
                case Cell.CELL_TYPE_STRING:
                    data.add(cell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        data.add(cell.getDateCellValue());
                    } else {
                        data.add(cell.getNumericCellValue());
                    }
                    break;
                default:
                    throw new Exception("");
            }
        }
        return data.toArray();
    }

    /**
     * Read row object [ ].
     *
     * @param file      the file
     * @param rowNumber the row number
     * @param classes   the classes
     * @return the object [ ]
     * @throws Exception the exception
     */
    public static Object[] readRow(Object file, int rowNumber, Class[] classes) throws Exception {
        Workbook workbook = (Workbook) file;
        Row row = workbook.getSheetAt(0).getRow(rowNumber);
        Iterator<Cell> iterator = row.iterator();
        List<Object> data = new ArrayList();
        for (int i = 0; i < classes.length; i++) {
            Cell cell = row.getCell(i);
            if (cell == null) {
                data.add("");
                data.set(data.size() - 1, null);
                continue;
            }
            Class c = classes[i];
        }
        return data.toArray();
    }
}
