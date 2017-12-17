package es.anpez.whoapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final EditText texto = findViewById(R.id.campodetexto);
    Button boton = findViewById(R.id.boton);

    boton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String text =  "Hola: " + texto.getText().toString();
        Toast.makeText(view.getContext(), text, Toast.LENGTH_LONG).show();
      }
    });
  }
}
