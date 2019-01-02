package whynot.com.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import whynot.com.dto.DtoGameData;
import whynot.com.quizzapp.R;

public class TopTenAdapter extends ArrayAdapter<DtoGameData> {
    private List<DtoGameData> list;
    private Context mContext;

    public TopTenAdapter(@NonNull Context context, @NonNull List<DtoGameData> list) {
        super(context, 0, list);
        this.list = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_view_item, parent, false);
        }
        DtoGameData data = list.get(position);
        ((TextView) listItem.findViewById(R.id.txvNick)).setText(data.getName());
        ((TextView) listItem.findViewById(R.id.txvPoints)).setText(data.getValue());

        return listItem;
    }

    public void setList(List<DtoGameData> list) {
        this.list = list;
    }
}
