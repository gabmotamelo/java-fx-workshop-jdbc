package workshopjavafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import workshopjavafx.db.DbException;
import workshopjavafx.listeners.DataChangeListener;
import workshopjavafx.model.entities.Seller;
import workshopjavafx.model.exceptions.ValidationException;
import workshopjavafx.model.services.SellerService;
import workshopjavafx.util.Alerts;
import workshopjavafx.util.Constraints;
import workshopjavafx.util.Utils;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormController implements Initializable  {

    private Seller entity;

    private SellerService service;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField textID;

    @FXML
    private TextField textName;

    @FXML
    private TextField textEmail;

    @FXML
    private DatePicker datePickerBirthDate;

    @FXML
    private TextField textBaseSalary;

    @FXML
    private Label labelErrorName;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorBirthDate;

    @FXML
    private Label labelErrorBaseSalary;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    public void setSeller(Seller entity){
        this.entity = entity;
    }

    public void setSellerService(SellerService service) {
        this.service = service;
    }

    public void subscribeDataChangeListener(DataChangeListener listener){
        dataChangeListeners.add(listener);
    }

    @FXML
    private void onBtSaveAction(ActionEvent event){
        if (entity == null){
            throw new IllegalStateException("Entity was null");
        } if ( service == null){
            throw new IllegalStateException("Service was null");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (DbException e){
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (ValidationException e){
            setErrorMessages(e.getErrors());
        }
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }

    private Seller getFormData() {
        Seller obj = new Seller();
        ValidationException exception = new ValidationException("Validation error");

        obj.setId(Utils.tryParseToInt(textID.getText()));

        if (textName.getText() == null || textName.getText().trim().equals("")){
            exception.addError("name", "Field can't be empty");
        }
        obj.setName(textName.getText());

        if (exception.getErrors().size() > 0){
            throw exception;
        }
        return obj;
    }

    @FXML
    private void onBtCancelAction(ActionEvent event){
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(textID);
        Constraints.setTextFieldMaxLength(textName,70);
        Constraints.setTextFieldMaxLength(textEmail, 70);
        Utils.formatDatePicker(datePickerBirthDate, "dd/MM/yyyy");
        Constraints.setTextFieldDouble(textBaseSalary);
    }

    public void updateFormData(){
        if (entity == null){
            throw new IllegalStateException("Entity was null");
        }
        textID.setText(String.valueOf(entity.getId()));
        textName.setText(entity.getName());
        textEmail.setText(entity.getEmail());
        if (entity.getBirthDate() != null) {
            datePickerBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }
        Locale.setDefault(Locale.US);
        textBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();
        if (fields.contains("name")){
            labelErrorName.setText(errors.get("name"));
        }

    }
}
