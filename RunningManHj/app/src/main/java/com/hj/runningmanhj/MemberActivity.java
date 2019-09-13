package com.hj.runningmanhj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MemberActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_FROM_ALBUM = 100;
    private static final int PICK_FROM_CAMERA = 200;
    private static final int CROP_FROM_IMAGE = 300;
    File tempFile;
    ImageView profile;
    Uri uri;
    String absoultePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        Log.i("MemberActivity", "dmdmdm");
        tedPermission();
        Button start_btn = findViewById(R.id.start_btn);
        Button modifiy_btn = findViewById(R.id.modifiy_btn);
        Button gallery_btn = findViewById(R.id.gallery_btn);
        profile = findViewById(R.id.profile);
        profile.setBackground(new ShapeDrawable(new OvalShape()));
        if(Build.VERSION.SDK_INT >= 21) {
            profile.setClipToOutline(true);
        }

        if(SaveSharedPreference.getUserName(this).length() == 0) {
            // call Login Activity
            Intent intent = new Intent(MemberActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        } else {

            TextView idView = (TextView)findViewById(R.id.id);
            idView.setText(SaveSharedPreference.getUserName(this));
        }
//        TextView idView = (TextView)findViewById(R.id.id);
//        SharedPreferences sharedPreferences = getSharedPreferences("MemberInfo",MODE_PRIVATE);
//        idView.setText(sharedPreferences.getString("id", ""));

        profile.setOnClickListener(this);
        start_btn.setOnClickListener(this);
        modifiy_btn.setOnClickListener(this);
        gallery_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.profile) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".png";
           // uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
            uri = FileProvider.getUriForFile(this, "{package name}.provider", new File(Environment.getExternalStorageDirectory(), url));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, PICK_FROM_CAMERA);
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//            try {
//                tempFile = createImageFile();
//            } catch (IOException e) {
//                Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//                finish();
//                e.printStackTrace();
//            }
//            if (tempFile != null) {
//
//                Uri photoUri = Uri.fromFile(tempFile);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                startActivityForResult(intent, PICK_FROM_CAMERA);
//            }
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            try {
//                tempFile = createImageFile();
//            } catch (IOException e) {
//                Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//                finish();
//                e.printStackTrace();
//            }
//
//            if (tempFile != null) {
//
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//
//                    Uri photoUri = FileProvider.getUriForFile(this,
//                            "{package name}.provider", tempFile);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                    startActivityForResult(intent, PICK_FROM_CAMERA);
//
//                } else {
//
//                    Uri photoUri = Uri.fromFile(tempFile);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                    startActivityForResult(intent, PICK_FROM_CAMERA);
//
//                }
//            }
        } else if (view.getId() == R.id.modifiy_btn) {


        } else if(view.getId() == R.id.gallery_btn) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_FROM_ALBUM);

        } else if (view.getId() == R.id.start_btn) {
            Intent intent = new Intent(MemberActivity.this, TeamActivity.class);
            startActivity(intent);
        }
    }

    //권한 요청을 해줍니다.
    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    //startActivityForResult 를 통해 다른 Activity 로 이동한 후 다시 돌아오게 되면 onActivityResult 가 동작
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_FROM_ALBUM) {
            Uri photoUri = data.getData();
            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = { MediaStore.Images.Media.DATA };

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            setImage();

        } else if (requestCode == PICK_FROM_CAMERA) {
            //ex1)
//            setImage();
             //ex2)
//            Bundle bundle = data.getExtras();
//            Bitmap bitmap = (Bitmap)bundle.get("data");
//            profile.setImageBitmap(bitmap);
            //ex3)
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, CROP_FROM_IMAGE);


        } else if(requestCode == CROP_FROM_IMAGE) {
//            if(resultCode != RESULT_OK) {
//                Log.i("membber", "999");
//                return;
//            }
            final Bundle extras = data.getExtras();
            //크롭된 이미지를 저장하기 위한 파일 경로
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/SmartWheel/" + System.currentTimeMillis() + ".png";
            if(extras != null) {
                Bitmap photo = extras.getParcelable("data");
                profile.setImageBitmap(photo);
                storeCropImage(photo, filePath);
                absoultePath = filePath;
            }
            File f = new File(uri.getPath());
            if(f.exists()) {
                f.delete();
            }
        }
    }

    private void storeCropImage(Bitmap bitmap, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel";
        File directory_SmartWheel = new File(dirPath);
        if(!directory_SmartWheel.exists()) {
            directory_SmartWheel.mkdir();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setImage() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        profile.setImageBitmap(originalBm);
        //백그라운드로 이미지 넣기
       // profile.setBackground(getResources().getDrawable(R.drawable.common_google_signin_btn_icon_dark));

    }

    //카메라에서 찍은 사진을 저장할 파일 만들기
    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "blackJin_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/blackJin/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".png", storageDir);

        return image;
    }
}
