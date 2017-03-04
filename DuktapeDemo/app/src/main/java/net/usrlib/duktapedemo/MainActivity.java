package net.usrlib.duktapedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.duktape.Duktape;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loadDuktape();
	}

	public void loadDuktape() {
		Duktape duktape = Duktape.create();

		try {

			JavaObject javaObject = msg -> {
				Log.d("MAIN", "javaObject: " + msg);
			};

			duktape.set("JavaObject", JavaObject.class, javaObject);

			duktape.evaluate(getString(R.string.javascript));

			JsObject jsObject = duktape.get("JsObject", JsObject.class);
			Log.d("MAIN", jsObject.getId());

			jsObject.sendCmd("LOAD");
		} finally {
			duktape.close();
		}
	}

	public interface JsObject {
		String getId();
		void sendCmd(String cmd);
	}

	public interface JavaObject {
		void onComplete(String msg);
	}
}
