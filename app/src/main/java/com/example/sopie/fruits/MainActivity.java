package com.example.sopie.fruits;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    buah buah = new buah();
    DBAdapter dbAdapter = null;

    EditText txtNama, txtWarna;
    ListView listbuah;
    Button btnSimpan;
    buah editbuah;

    private static final String OPTION[] =
            {"Edit", "Delete"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new DBAdapter(getApplicationContext());
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        txtNama = (EditText) findViewById(R.id.txtNama);
        txtWarna = (EditText) findViewById(R.id.txtWarna);
        listbuah = (ListView) findViewById(R.id.listbuah);


        listbuah.setOnItemClickListener(new ListItemClick());
        listbuah.setAdapter(new ListItemClick.ListbuahAdapter(dbAdapter.getAllbuah()));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public class ListItemClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position long id)
        {

            //TODO Auto-generated method stub
            final buah buah = (buah) listbuah.getItemAtPosition(position);
            showOptionDialog(buah);
        }
        public void showOptionDialog(buah buah){
            final buah mbuah;
            mbuah = buah;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("Test")
                    .setItems(OPTION, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int post) {
                            //TODO Auto-generated method stub
                            switch (post){
                                case 0:
                                    editbuah = mbuah;
                                    txtNama.setText(mbuah.getNama());
                                    txtWarna.setText(mbuah.getWarna());
                                    btnSimpan.setText("Edit");
                                    break;
                                case 1:
                                    dbAdapter.delete(mbuah);
                                    listbuah.setAdapter(new ListbuahAdapter(dbAdapter.getAllbuah()));
                                    break;
                                default:
                                    break;

                            }
                        }
                    });
            final Dialog d = builder.create();
            d.show();
        }


        public void save(View v){
            if(txtNama.getText().length() == 0 ||
                    txtWarna.getText().length() == 0){
                txtNama.setError("Cannot Empty");
                txtWarna.setError("Cannot Empty");
            }else {
                if(btnSimpan.getText().equals("Edit")){
                    editbuah.setNama(txtNama.getText().toString());
                    editbuah.setWarna(txtWarna.getText().toString());
                    dbAdapter.updatebuah(editbuah);
                    btnSimpan.setText("Simpan");
                }else {
                    buah.setNama(txtNama.getText().toString());
                    buah.setWarna(txtWarna.getText().toString());
                    dbAdapter.save(buah);
                }
                txtNama.setText("");
                txtWarna.setText("");
            }
            listbuah.setAdapter(new ListbuahAdapter(dbAdapter.getAllbuah()));

        }

        public class ListbuahAdapter extends BaseAdapter{
            private List<buah> listbuahs;

            public ListbuahAdapter (List<buah> listbuahs){
                this.listbuahs = listbuahs;
            }
            @Override
            public int getCount(){
                // TODO Auto-generated method stub
                return this.listbuahs.size();

            }
            @Override
            public buah getItem(int position){
                return this.listbuahs.get(position);
            }
            @Override
            public long getItemId(int position){
                //TODO Auto-generated method stub
                return position;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                //TODO Auto-generated method stub
                if (convertView == null){
                    convertView = LayoutInflater
                            .from(getApplicationContext())
                            .inflate(R.layout.list_layout, parent false);
                }final buah buah = getItem(position);
                if (buah != null){
                    TextView labelNama = (TextView) convertView
                            .findViewById(R.id.labelNama);
                    labelNama.setText(buah.getNama());
                    TextView labelWarna = (TextView) convertView
                            .findViewById(R.id.labelWarna);
                    labelWarna.setText(buah.getWarna());
                }
                return convertView;
            }

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



}