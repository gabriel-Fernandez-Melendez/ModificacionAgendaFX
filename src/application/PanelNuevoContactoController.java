package application;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import entidades.Contacto;
import entidades.ControladorContactos;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PanelNuevoContactoController extends AnchorPane{

	//aqui deben ir las propiedades de la ventana con las que vamos a necesitar interactuar
	@FXML
	private TextField campo_nombre;
	@FXML
	private TextField campo_telefono;
	@FXML
	private TextField campo_email;
	@FXML
	private RadioButton radiob_masculino;
	@FXML
	private RadioButton radiob_femenino;
	@FXML
	private ChoiceBox<String> grupo_;
	@FXML
	private TextField notas_adicionales;
	@FXML
	private DatePicker fechadenacimiento;
	@FXML
	private Image imagen_;
	@FXML
	private Button borrar;
	@FXML
	private Button añadir;
	@FXML
	private Button atras;
	@FXML
	private CheckBox esfavorito;

	
	 public PanelNuevoContactoController(){
		 
		 System.out.println("estamos en el constructor del panel");
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("panelnuevocontacto.fxml"));

	        fxmlLoader.setRoot(this);
	        fxmlLoader.setController(this);
	        try {
	           fxmlLoader.load();
	           fechadenacimiento.getEditor().setDisable(true);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 //apartir de aqui se deben hacer los metodos de funcionalidad de la ventana
	 @FXML
	 public String grupogeneros(){
		 String valor;
		 ToggleGroup t =new ToggleGroup();
		 if(t.getSelectedToggle()!=null) {
		radiob_masculino.setText("Masculino");
		 radiob_femenino.setText("Femenino");
		 radiob_masculino.setToggleGroup(t);
		 radiob_femenino.setToggleGroup(t);
		 RadioButton selec = (RadioButton) t.getSelectedToggle();	
		 valor=selec.getText();
		 }
		 else {
			 valor="no";
		 }
		 return valor;
	 }
	 @FXML
	 public void grupo_combobox() {
		 grupo_.setItems(FXCollections.observableArrayList("Amigos", "Familia", "trabajo"));
		 grupo_.setValue("Amigos");
		 handleListView();
	 }
	 @FXML
	 private void handleListView() {
		 grupo_.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				System.out.println("Elemento seleccionado: " + newValue);
			});
		}
	 @FXML
	 public void RecogerFecha() {
		 LocalDate localDate = fechadenacimiento.getValue();
		 System.out.println(localDate +"por ejemplo");
	 }
	 
	 @FXML
	 public void LimpiarCampos(){
		 Alert aviso= new Alert(AlertType.CONFIRMATION);
		 aviso.setContentText("se borrara el contenido de los campos,¿ esta seguro que quiere continuar?");
		 aviso.setTitle("limpiar los campos");
		 Optional<ButtonType> result =aviso.showAndWait();
		 if(result.get()==ButtonType.OK) {
			campo_nombre.clear();
		 campo_telefono.clear();
		 campo_email.clear();
		 notas_adicionales.clear();
		 radiob_femenino.setSelected(false);
		 radiob_masculino.setSelected(false); 
		 System.out.println("se limpiaron los campos");
		 }
		 else {
			 System.out.println("prueba de que no pasa por aqui si se da click a cancel");
		 } 
	 }
	 
	 @FXML
	 public void GuardarContacto() {
		 grupo_combobox();
		 String grupogen=grupogeneros();
		 if(campo_email.getText().isBlank()==false&&campo_nombre.getText().isBlank()==false&&campo_telefono.getText().isBlank()==false&&grupogen=="no"&&grupo_.getValue().isEmpty()==false&&notas_adicionales.getText().isBlank()==false&&fechadenacimiento.getValue()!=null) {
			Contacto con = new Contacto();
		 Alert nuevo = new Alert(AlertType.CONFIRMATION);
		 nuevo.setContentText("Seguro que quiere guardar los datos del contacto ?");
		 nuevo.setTitle("Nuevo contacto");
		 Optional<ButtonType> result =nuevo.showAndWait();
		 if(result.get()==ButtonType.OK) {
			 String nombre = campo_nombre.getText();
			 String telefono=campo_telefono.getText();
			 String email=campo_email.getText();		 
			 String genero=grupogeneros();
			 String grupo=grupo_.getValue();
			 String notas_ad=notas_adicionales.getText();
			 LocalDate fechanac = fechadenacimiento.getValue();
			 boolean fav = esfavorito.isSelected();
			 System.out.println("pasaporaqui");
			 con.setNombre(nombre);
			 con.setTelefono(telefono);
			 con.setEmail(email);
			 con.setGenero(genero);
			 con.setGrupo(grupo);
			 String aux=ControladorContactos.NotasAdicionales(notas_ad);
			 con.setNotas(aux);
			 con.setFechanac(fechanac);
			 con.setFavorito(fav);
			 ControladorContactos.escribir(con);
			 Alert exito = new Alert(AlertType.INFORMATION);
			 exito.setContentText("Se ha guardado el contacto de nombre: "+con.getNombre() + " con exito!");
			 exito.setTitle("Contacto Guardado");
			 exito.showAndWait();
		 }
		 else {
			 System.out.println("decidio aun  no añadir al contacto");
		 }
		 }
		 else {
			 Alert datos = new Alert(AlertType.INFORMATION);
			 datos.setContentText("ingrese los datos que faltan del contacto");
			 datos.setTitle("faltan datos");
			 datos.showAndWait();
	     }	 
	 }
	 
	 
}
