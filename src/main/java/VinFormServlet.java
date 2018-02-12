import com.google.common.collect.ImmutableMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "VinFormServlet", urlPatterns = "/vinform")
public class VinFormServlet extends HttpServlet {

    private String vinValue;
    Map<String, String> WMI = new HashMap<String, String>() {{
      
    }};

    Map<String, Integer> Alphabet = new HashMap<String, Integer>() {{
        put("A", 1);
        put("B", 2);
        put("C", 3);
        put("D", 4);
        put("E", 5);
        put("F", 6);
        put("G", 7);
        put("H", 8);
        put("J", 1);
        put("K", 2);
        put("L", 3);
        put("M", 4);
        put("N", 5);
        put("P", 7);
        put("R", 9);
        put("S", 2);
        put("T", 3);
        put("U", 4);
        put("V", 5);
        put("W", 6);
        put("X", 7);
        put("Y", 8);
        put("Z", 9);
    }};

    //Map<String, Integer> left = ImmutableMap.of("A", 1, "B", 2, "C", 3, "D", 4, "D",);


    int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 0, 1,
            2, 3, 4, 5, 0, 7, 0, 9, 2, 3,
            4, 5, 6, 7, 8, 9};
    int[] weights = {8, 7, 6, 5, 4, 3, 2, 10, 0, 9,
            8, 7, 6, 5, 4, 3, 2};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.setContentType( "text/html" );
        vinValue = req.getParameter("vinnumber");
        StringBuffer utilOutput = new StringBuffer();
        StringBuffer utilStyle = new StringBuffer();

        PrintWriter out = resp.getWriter();
        boolean result = false;
        try {
            result = checkVin(vinValue);
        } catch (Exception e) {

            utilOutput.append(e.getMessage());
            utilStyle.append(" .form input[type=text] {  background-color: red; }");
            result = false;
        } finally {
            if (result) {
                utilOutput.append("Serial number: " + getSerialNumber());
                utilStyle.append(" .form input[type=text] {  background-color: green; }");
            }
            req.setAttribute("utilOutput", utilOutput.toString());
            req.setAttribute("utilStyle", utilStyle.toString());
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }

    private boolean checkVin(String vin) {
        vinValue = vin.toUpperCase().replaceAll("-", "");
        if (!isValidLength()) {
            throw new IllegalArgumentException("Invalid legth");
        }
        if (!isValidCharacters()) {
            throw new IllegalArgumentException("Invalid characters I,O,Q");
        }
        if (!isValidCheckSum()) {
            throw new IllegalArgumentException("Invalid checksum");
        }
        /*if (!isValidWMI()) {

        }
        if (!isValidVDS()) {

        }*/
        return true;
    }

    private boolean isValidCheckSum() {
        int sum = 0;
        char check = vinValue.charAt(8);
        if (check != 'X' && (check < '0' || check > '9')) {
            throw new IllegalArgumentException("Illegal check digit: " + check);
        }

        // for (char c : vinValue.toCharArray()) {

        for (int i = 0; i < 17; i++) {
            int value = 0;
            char c = vinValue.charAt(i);
            // letter
            if (c >= 'A' && c <= 'Z') {
                value = values[c - 'A'];
                //      if (value == 0)
                //         throw new IllegalArgumentException("Illegal character: " + c);
            }
            // number
            else if (c >= '0' && c <= '9') {
                value = c - '0';
            }
            // illegal character
            // else throw new IllegalArgumentException("Illegal character: " + c);

            //sum = Alphabet.get(c) * value;

            sum += value * weights[i];
        }

        sum %= 11;
        if (sum == 10 && check == 'X') return true;
        else if (sum == Character.getNumericValue(check)) return true;
        else return false;

    }

    private boolean isValidLength() {
        return vinValue.length() == 17 ? true : false;
    }

    private boolean isValidWMI() {
        return false;
    }

    private String getSerialNumber() {
        return vinValue.substring(10, 17);
    }

    private String getModel() {
        return "";
    }

    private String getRudder() {
        return WMI.get(vinValue.substring(0, 2));
    }

    private boolean isValidCharacters() {// I, O and Q are not allowed
        return vinValue.matches("^.*?(I|O|Q).*$") ? false : true;
    }
}
