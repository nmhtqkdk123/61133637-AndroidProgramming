package ntu.nmh61133637.shopeeapplicationfinalexam;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        // Required empty public constructor
    }
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        try (tb_AccountHandler tb_account = new tb_AccountHandler(getContext())) {
            Account account = tb_account.getAccount(1);
            TextView accountName = view.findViewById(R.id.accountName);
            accountName.setText(account.getName());
            TextView accountID = view.findViewById(R.id.accountID);
            accountID.setText(account.getStudentID());
            TextView accountClass = view.findViewById(R.id.accountClass);
            accountClass.setText(account.getClassName());
            EditText accountEmail = view.findViewById(R.id.accountEmail);
            accountEmail.setText(account.getEmail());
            EditText accountPhone = view.findViewById(R.id.accountPhone);
            accountPhone.setText(account.getPhoneNumber());
            accountEmail.setOnEditorActionListener((textView, actionId, keyEvent) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_NEXT) {
                    account.setEmail(accountEmail.getText().toString());
                    tb_account.editAccount(account.getId(), account);
                    InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    Toast.makeText(getContext(), "Thay đổi địa chỉ Email thành công.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
            accountPhone.setOnEditorActionListener((textView, actionId, keyEvent) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_NEXT) {
                    account.setPhoneNumber(accountPhone.getText().toString());
                    tb_account.editAccount(account.getId(), account);
                    InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    Toast.makeText(getContext(), "Thay đổi số điện thoại thành công.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
        }
        return view;
    }
}