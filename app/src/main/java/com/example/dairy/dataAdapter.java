package com.example.dairy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class dataAdapter extends RecyclerView.Adapter<dataAdapter.JasonDataViewHolder> {
    public static final String EDIT_URL = "https://sinchdairy.000webhostapp.com/projectd/update.php";
    //public static final String DELETE_URL = "https://sinchdairy.000webhostapp.com/projectd/delete.php";
    public LayoutInflater layoutInflater;
    ArrayList<JsonDataList> jsonDataLists;
    Context context;
    int id;
    public dataAdapter(Context context) {
        jsonDataLists = new ArrayList<>();
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<JsonDataList> jsonDataLists) {
        this.jsonDataLists = jsonDataLists;
    }

    @NonNull
    @Override
    public JasonDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //  Context context = parent.getContext();
        // LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dataView = layoutInflater.inflate(R.layout.recycle_row, parent, false);
        return new JasonDataViewHolder(dataView);
    }

    @Override
    public void onBindViewHolder(@NonNull JasonDataViewHolder holder, int position) {
        JsonDataList jsonDataList = jsonDataLists.get(position);
        holder.datedis.setText(jsonDataList.datedis);
        holder.from_time.setText(jsonDataList.from_time);
        holder.to_time.setText(jsonDataList.to_time);
        holder.portion.setText(jsonDataList.portion);
        holder.tclass.setText(jsonDataList.tclass);
        holder.id.setText(jsonDataList.id);

        holder.edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View editLayout = LayoutInflater.from(context).inflate(R.layout.edit_data, null);
                EditText Date = editLayout.findViewById(R.id.edit_date);
                EditText From_time = editLayout.findViewById(R.id.edit_fot);
                EditText To_time = editLayout.findViewById(R.id.edit_tot);
                EditText Tclass = editLayout.findViewById(R.id.edit_classn);
                EditText Portion = editLayout.findViewById(R.id.edit_portion);

                Date.setText(jsonDataList.getDatedis());
                From_time.setText(jsonDataList.getFrom_time());
                To_time.setText(jsonDataList.getTo_time());
                Tclass.setText(jsonDataList.getTclass());
                Portion.setText(jsonDataList.getPortion());


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit" + jsonDataList.getDatedis());
                builder.setView(editLayout);


                builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        final String date = Date.getText().toString();
                        final String from_time = From_time.getText().toString();
                        final String to_time = To_time.getText().toString();
                        final String tclass = Tclass.getText().toString();
                        final String portion = Portion.getText().toString();
                        final String id = jsonDataList.getId();
                        if (date.isEmpty() || from_time.isEmpty() || to_time.isEmpty() || tclass.isEmpty() || portion.isEmpty()) {
                            Toast.makeText(context, "Some fields are Empty", Toast.LENGTH_SHORT).show();
                        } else {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, EDIT_URL,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("date", date);
                                    params.put("from_time", from_time);
                                    params.put("to_time", to_time);
                                    params.put("tclass", tclass);
                                    params.put("portion", portion);
                                    params.put("workdone_id", String.valueOf(id));
                                    return params;

                                }
                            };
                            RequestQueue queue = Volley.newRequestQueue(context);
                            queue.add(stringRequest);
                        }
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder=new AlertDialog.Builder(context);
//                builder.setTitle("Delete Data");
//                builder.setMessage("Confirm to Delete" +jsonDataList.getId());
//                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        final String id = jsonDataList.getId();
//                        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_URL,
//                                new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//                                        Toast.makeText(context,response, Toast.LENGTH_SHORT).show();
//
//                                            if (response.equals("delete")){
//                                                jsonDataList.remove("deleted");
//                                                Toast.makeText(context, "Deleted Successfull", Toast.LENGTH_SHORT).show();
//                                            }
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(context,error.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }){
//                            @Override
//                            protected Map<String, String> getParams() throws AuthFailureError {
//                                HashMap<String,String> deleteparams = new HashMap<>();
//                                deleteparams.put("id",jsonDataList.getId());
//                                return deleteparams;
//                            }
//                        };
//                        RequestQueue queue = Volley.newRequestQueue(context);
//                        queue.add(stringRequest);
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return jsonDataLists.size();
    }

    public class JasonDataViewHolder extends RecyclerView.ViewHolder {
        TextView datedis, from_time, to_time, tclass, portion, id;
        Button edit;
        //ImageButton delete;
        //Layout editLayout;


        public JasonDataViewHolder(@NonNull View itemView) {
            super(itemView);
            datedis = itemView.findViewById(R.id.datedis);
            from_time = itemView.findViewById(R.id.from_time);
            to_time = itemView.findViewById(R.id.to_time);
            tclass = itemView.findViewById(R.id.tclass);
            portion = itemView.findViewById(R.id.portion);
            id = itemView.findViewById(R.id.ids);
            edit= itemView.findViewById(R.id.edit_rcy);
            //delete = itemView.findViewById(R.id.imageButton);

        }
    }
//    public void delete(int item){
//        jsonDataLists.remove(item);
//        notifyItemRemoved(item);
//
//    }
}