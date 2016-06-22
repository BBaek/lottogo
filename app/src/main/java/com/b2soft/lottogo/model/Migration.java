package com.b2soft.lottogo.model;

import android.content.Context;
import android.util.Log;

import com.b2soft.lottogo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by woonsungbaek on 2016. 6. 22..
 */
public class Migration implements RealmMigration {
    Context context;

    public Migration(Context context) {
        this.context = context;
        // init(copy) database file
        initRealm();
    }

    private void initRealm() {
        final String REALMFILE = "lottogo.realm";

        // copy raw file to realm
        String path = context.getFilesDir().getPath() + "/" + REALMFILE;
//        Log.d("Migration", context.getFilesDir().getPath());
//        Log.d("Migration", "lotto.realm = exists: " + new File(context.getFilesDir().getPath() + "/lotto.realm").exists());
        boolean exists = new File(path).exists();
        Log.d("Migration", "lottogo.realm = exists: " + exists);
        if (!exists) {
            copyBundledRealmFile(context.getResources().openRawResource(R.raw.lotto), REALMFILE);
        }

        RealmConfiguration config = new RealmConfiguration.Builder(context)
                .name(REALMFILE)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();

        // set default Realm
        Realm.setDefaultConfiguration(config);
    }

    private String copyBundledRealmFile(InputStream inputStream, String outFileName) {
        try {
            File file = new File(context.getFilesDir(), outFileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {
        // During a migration, a DynamicRealm is exposed. A DynamicRealm is an untyped variant of a normal Realm, but
        // with the same object creation and query capabilities.
        // A DynamicRealm uses Strings instead of Class references because the Classes might not even exist or have been
        // renamed.

        // Access the Realm schema in order to create, modify or delete classes and their fields.
        RealmSchema schema = realm.getSchema();

        /************************************************
         // Version 0
         class Person
         @Required
         String firstName;
         @Required
         String lastName;
         int    age;
         // Version 1
         class Person
         @Required
         String fullName;            // combine firstName and lastName into single field.
         int age;
         ************************************************/
        // Migrate from version 0 to version 1
//        if (oldVersion == 0) {
//            RealmObjectSchema personSchema = schema.get("Person");
//
//            // Combine 'firstName' and 'lastName' in a new field called 'fullName'
//            personSchema
//                    .addField("fullName", String.class, FieldAttribute.REQUIRED)
//                    .transform(new RealmObjectSchema.Function() {
//                        @Override
//                        public void apply(DynamicRealmObject obj) {
//                            obj.set("fullName", obj.getString("firstName") + " " + obj.getString("lastName"));
//                        }
//                    })
//                    .removeField("firstName")
//                    .removeField("lastName");
//            oldVersion++;
//        }
//
//        /************************************************
//         // Version 2
//         class Pet                   // add a new model class
//         @Required
//         String name;
//         @Required
//         String type;
//         class Person
//         @Required
//         String fullName;
//         int age;
//         RealmList<Pet> pets;    // add an array property
//         ************************************************/
//        // Migrate from version 1 to version 2
//        if (oldVersion == 1) {
//
//            // Create a new class
//            RealmObjectSchema petSchema = schema.create("Pet")
//                    .addField("name", String.class, FieldAttribute.REQUIRED)
//                    .addField("type", String.class, FieldAttribute.REQUIRED);
//
//            // Add a new field to an old class and populate it with initial data
//            schema.get("Person")
//                    .addRealmListField("pets", petSchema)
//                    .transform(new RealmObjectSchema.Function() {
//                        @Override
//                        public void apply(DynamicRealmObject obj) {
//                            if (obj.getString("fullName").equals("JP McDonald")) {
//                                DynamicRealmObject pet = realm.createObject("Pet");
//                                pet.setString("name", "Jimbo");
//                                pet.setString("type", "dog");
//                                obj.getList("pets").add(pet);
//                            }
//                        }
//                    });
//            oldVersion++;
//        }
    }
}
