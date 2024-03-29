package PlaceholderTemplate;

import PlaceholderTemplate.Dao.InputFields;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.OutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocxWorker {

    public static List<InputFields> getAllInputFieldsName(String inputFilePath) throws Exception {
        List<InputFields> result = new ArrayList<>(Collections.emptyList());
        XWPFDocument doc = new XWPFDocument(OPCPackage.open(inputFilePath));

        for (XWPFParagraph p : doc.getParagraphs()) {
            result.addAll(findInputFieldsName(p));
        }
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        result.addAll(findInputFieldsName(p));
                    }
                }
            }
        }
        return result;
    }

    public static void replace(String inFile, Map<String, String> data, OutputStream out) throws Exception {
        XWPFDocument doc = new XWPFDocument(OPCPackage.open(inFile));
        for (XWPFParagraph p : doc.getParagraphs()) {
            replace2(p, data);
        }
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        replace2(p, data);
                    }
                }
            }
        }
        doc.write(out);
    }

    private static void replace2(XWPFParagraph p, Map<String, String> data) {
        String pText = p.getText(); // complete paragraph as string
        if (pText.contains("${")) { // if paragraph does not include our pattern, ignore
            TreeMap<Integer, XWPFRun> posRuns = getPosToRuns(p);
            Pattern pat = Pattern.compile("\\$\\{(.+?)\\}");
            Matcher m = pat.matcher(pText);
            while (m.find()) { // for all patterns in the paragraph
                String g = m.group(1);  // extract key start and end pos
                int s = m.start(1);
                int e = m.end(1);
                String key = g;
                String x = data.get(key);
                if (x == null)
                    x = "";
                SortedMap<Integer, XWPFRun> range = posRuns.subMap(s - 2, true, e + 1, true); // get runs which contain the pattern
                boolean found1 = false; // found $
                boolean found2 = false; // found {
                boolean found3 = false; // found }
                XWPFRun prevRun = null; // previous run handled in the loop
                XWPFRun found2Run = null; // run in which { was found
                int found2Pos = -1; // pos of { within above run
                for (XWPFRun r : range.values()) {
                    if (r == prevRun)
                        continue; // this run has already been handled
                    if (found3)
                        break; // done working on current key pattern
                    prevRun = r;
                    for (int k = 0; ; k++) { // iterate over texts of run r
                        if (found3)
                            break;
                        String txt = null;
                        try {
                            txt = r.getText(k); // note: should return null, but throws exception if the text does not exist
                        } catch (Exception ex) {

                        }
                        if (txt == null)
                            break; // no more texts in the run, exit loop
                        if (txt.contains("$") && !found1) {  // found $, replace it with value from data map
                            txt = txt.replaceFirst("\\$", x);
                            found1 = true;
                        }
                        if (txt.contains("{") && !found2 && found1) {
                            found2Run = r; // found { replace it with empty string and remember location
                            found2Pos = txt.indexOf('{');
                            txt = txt.replaceFirst("\\{", "");
                            found2 = true;
                        }
                        if (found1 && found2 && !found3) { // find } and set all chars between { and } to blank
                            if (txt.contains("}")) {
                                if (r == found2Run) { // complete pattern was within a single run
                                    txt = txt.substring(0, found2Pos) + txt.substring(txt.indexOf('}'));
                                } else // pattern spread across multiple runs
                                    txt = txt.substring(txt.indexOf('}'));
                            } else if (r == found2Run) // same run as { but no }, remove all text starting at {
                                txt = txt.substring(0, found2Pos);
                            else
                                txt = ""; // run between { and }, set text to blank
                        }
                        if (txt.contains("}") && !found3) {
                            txt = txt.replaceFirst("\\}", "");
                            found3 = true;
                        }
                        r.setText(txt, k);
                    }
                }
            }
            //System.out.println(p.getText());

        }

    }

    private static TreeMap<Integer, XWPFRun> getPosToRuns(XWPFParagraph paragraph) {
        int pos = 0;
        TreeMap<Integer, XWPFRun> map = new TreeMap<Integer, XWPFRun>();
        for (XWPFRun run : paragraph.getRuns()) {
            String runText = run.text();
            if (runText != null && runText.length() > 0) {
                for (int i = 0; i < runText.length(); i++) {
                    map.put(pos + i, run);
                }
                pos += runText.length();
            }

        }
        return map;
    }


    private static List<InputFields> findInputFieldsName(XWPFParagraph p) {
        List<InputFields> result = new ArrayList<>(Collections.emptyList());
        String pText = p.getText(); // complete paragraph as string
        if (pText.contains("${")) { // if paragraph does not include our pattern, ignore
            TreeMap<Integer, XWPFRun> posRuns = getPosToRuns(p);
            Pattern pat = Pattern.compile("\\$\\{(.+?)\\}");
            Matcher m = pat.matcher(pText);
            while (m.find()) { // for all patterns in the paragraph
                String g = m.group(1);  // extract key start and end pos
                int s = m.start(1);
                int e = m.end(1);
                SortedMap<Integer, XWPFRun> range = posRuns.subMap(s - 2, true, e + 1, true); // get runs which contain the pattern
                boolean found1 = false; // found $
                boolean found2 = false; // found {
                boolean found3 = false; // found }
                XWPFRun prevRun = null; // previous run handled in the loop
                XWPFRun found2Run = null; // run in which { was found
                int found2Pos = -1; // pos of { within above run
                for (XWPFRun r : range.values()) {
                    if (r == prevRun)
                        continue; // this run has already been handled
                    if (found3)
                        break; // done working on current key pattern
                    prevRun = r;
                    for (int k = 0; ; k++) { // iterate over texts of run r
                        if (found3)
                            break;
                        String txt = null;
                        try {
                            txt = r.getText(k); // note: should return null, but throws exception if the text does not exist
                        } catch (Exception ex) {

                        }
                        if (txt == null)
                            break; // no more texts in the run, exit loop
                        if (txt.contains("$") && !found1) {  // found $, replace it with value from data map
                            txt = txt.replaceFirst("\\$", "");
                            found1 = true;
                        }
                        if (txt.contains("{") && !found2 && found1) {
                            found2Run = r; // found { replace it with empty string and remember location
                            found2Pos = txt.indexOf('{');
                            txt = txt.replaceFirst("\\{", "");
                            found2 = true;
                        }
                        if (found1 && found2 && !found3) { // find } and set all chars between { and } to blank
                            if (txt.contains("}")) {
                                if (r == found2Run) { // complete pattern was within a single run
                                    StringBuilder stringBuilder = new StringBuilder();
                                    String value = txt.substring(found2Pos, txt.indexOf('}'));
                                    String key = DigestUtils.md5Hex(
                                            stringBuilder
                                                    .append(value)
                                                    .append(java.util.Calendar.getInstance().getTime().toString()).toString()).toUpperCase();

                                    InputFields inputFields = new InputFields(key, value);
                                    result.add(inputFields);
                                    txt = txt.substring(0, found2Pos) + txt.substring(txt.indexOf('}'));
                                } else // pattern spread across multiple runs
                                {
                                    String value = result.get(result.size() - 1).value + txt.substring(0, txt.length() - 1);
                                    InputFields inputFields = new InputFields(result.get(result.size() - 1).key, value);
                                    result.set(result.size() - 1, inputFields);
                                    txt = txt.substring(txt.indexOf('}'));
                                }
                            } else if (r == found2Run) // same run as { but no }, remove all text starting at {
                            {
                                txt = txt.substring(0, found2Pos);
                            } else {
                                StringBuilder stringBuilder = new StringBuilder();
                                InputFields newInputFields = new InputFields(DigestUtils.md5Hex(
                                        stringBuilder
                                                .append(txt)
                                                .append(java.util.Calendar.getInstance().getTime().toString()).toString()).toUpperCase(), txt);
                                result.add(newInputFields);
                                txt = ""; // run between { and }, set text to blank
                            }
                        }
                        if (txt.contains("}") && !found3) {
                            txt = txt.replaceFirst("\\}", "");
                            found3 = true;
                        }
                        r.setText(txt, k);
                    }
                }
            }
        }
        return result;
    }

}
