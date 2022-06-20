package util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextField;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {
        public static Object validate(LinkedHashMap<TextField, Pattern> map, JFXButton btn, JFXButton btn2) {
            for (TextField key : map.keySet()) {
                Pattern pattern = map.get(key);
                if (!pattern.matcher(key.getText()).matches()){
                    //if the input is not matching
                    addError(key,btn,btn2);
                    return key;
                }
                removeError(key,btn,btn2);
            }
            return true;
        }

        private static void removeError(TextField txtField,JFXButton btn,JFXButton btn2) {
            txtField.setStyle("-fx-border-color: green");
            btn.setDisable(false);
            btn2.setDisable(false);
        }

        private static void addError(TextField txtField,JFXButton btn,JFXButton btn2) {
            if (txtField.getText().length() > 0) {
                txtField.setStyle("-fx-border-color: red");
            }
            btn.setDisable(true);
            btn2.setDisable(true);
        }
}
