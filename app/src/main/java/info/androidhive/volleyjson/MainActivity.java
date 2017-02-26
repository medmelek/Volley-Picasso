package info.androidhive.volleyjson;

import info.androidhive.volleyjson.Adapter.Adapter;
import info.androidhive.volleyjson.DataBase.Database;
import info.androidhive.volleyjson.R;
import info.androidhive.volleyjson.app.AppController;
import info.androidhive.volleyjson.app.MySingleton;
import info.androidhive.volleyjson.app.modeltmp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import java.util.Queue;

public class MainActivity extends Activity {


	private String urlJsonObj = "http://www.prevision-meteo.ch/services/json/paris";
	String url;
	private static String TAG = MainActivity.class.getSimpleName();
	private Button btnMakeObjectRequest;

	private ProgressDialog pDialog;
	//ImageRequest request ;
	private String jsonResponse;
	RecyclerView myRecyclerView;
	LinearLayoutManager linearLayoutManager;
	Adapter tmpAdapter;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnMakeObjectRequest = (Button) findViewById(R.id.btnObjRequest);

		myRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		linearLayoutManager = new LinearLayoutManager(this);



		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);

		btnMakeObjectRequest.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				makeJsonObjectRequest();
				affiche();

				//Picasso.with(getApplicationContext()).load("http://www.prevision-meteo.ch/style/images/icon/eclaircies.png").into(imagee);
				/*request = new ImageRequest("http://www.prevision-meteo.ch/style/images/icon/eclaircies.png",new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap bitmap) {
						imagee.setImageBitmap(bitmap);
						//image.setImageBitmap(bitmap);
					}
				}, 0, 0, null,
						new Response.ErrorListener() {
							public void onErrorResponse(VolleyError error) {
							Toast.makeText(getApplicationContext(),"error image loader",Toast.LENGTH_LONG).show();
							}
						});

				MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);*/


			}
		});


		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	/**
	 * Method to make json object request where json response starts wtih {
	 */
	private void makeJsonObjectRequest() {

		showpDialog();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				urlJsonObj, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, response.toString());

				try {

					//String name = response.getJSONObject("city_info").get("name").toString();
					String date ="du date :"+ response.getJSONObject("current_condition").get("date").toString();
					String tmp ="Température est : "+ response.getJSONObject("current_condition").get("tmp").toString();
					url = response.getJSONObject("current_condition").get("icon").toString();
					//	txtResponse.setText("City is"+name +"\nCountry name is "+country+"\nTmp is "+tmp+" °C");
					Database.getInstance(getApplicationContext()).addtmp(new modeltmp(tmp, url, date));

				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(),
							"Error: " + e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
				hidepDialog();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

				Toast.makeText(getApplicationContext(),"Connexion not found !", Toast.LENGTH_SHORT).show();
				hidepDialog();
			}
		});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);


	}

    private  void affiche () {
		myRecyclerView.setLayoutManager(linearLayoutManager);
		tmpAdapter = new Adapter(this, Database.getInstance(this).getAlltmp());
		myRecyclerView.setAdapter(tmpAdapter);
	}
	private void showpDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hidepDialog() {
		if (pDialog.isShowing())
			pDialog.dismiss();
	}

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	public Action getIndexApiAction() {
		Thing object = new Thing.Builder()
				.setName("Main Page") // TODO: Define a title for the content shown.
				// TODO: Make sure this auto-generated URL is correct.
				.setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
				.build();
		return new Action.Builder(Action.TYPE_VIEW)
				.setObject(object)
				.setActionStatus(Action.STATUS_TYPE_COMPLETED)
				.build();
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		AppIndex.AppIndexApi.start(client, getIndexApiAction());
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		AppIndex.AppIndexApi.end(client, getIndexApiAction());
		client.disconnect();
	}
}
