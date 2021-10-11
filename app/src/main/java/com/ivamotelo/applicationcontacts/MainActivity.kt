/**
 * O primeiro passo é solicitar a permissão do usuário para acessar os seus contatos
 * Aṕos, criar uma função para criar uma tabela vazia de contatos e percorrer com um
 * cursor, conforme estrutura das tabelas 'contact' do Google da classe ' ContactsContract'.
 * Ver documentação:
 * https://developer.android.com/guide/topics/providers/contacts-provider?hl=pt-br
 *
 * Solicita à aplicação para checar se a mesma possui autorização de leitura dos contatos
 * logo, se a autorização for DIFERENTE de 'PERMISSION_GRANTED (Já existe a permissão), então
 * será preciso de pedir a autorização, caso contrário (se já foi dada a permissão), então
 *
 * Criaçõa de uma lista nova, vazia de contatos
 * Criação de um cursor para percorrer a lista, item a item, conforme estrutura das
 * tabelas do Provedor de contatos 'contact' do Google (ver documentação)
 *  do conteiner ContactsContract, com 'CommonDataKinds' -> tipos de dados comuns,
 */
package com.ivamotelo.applicationcontacts

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val REQUEST_CONTACT = 1 // identificador de autorização para leitura de contatos
    val LINEAR_LAYOUT_VERTICAL = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                REQUEST_CONTACT)
        } else {
            setContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CONTACT) setContacts()
    }

    @SuppressLint("Range")
    private fun setContacts() {
        val contactList: ArrayList<Contacts> = ArrayList()

        val cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null)

        if (cursor != null) {
            while (cursor.moveToNext()) {
                contactList.add(Contacts(
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                ))
            }
            cursor.close()
        }

        val adapter = ContactsAdapter(contactList)
        val contactRecyclerView = findViewById<RecyclerView>(R.id.recycle_view_contatcts)

        contactRecyclerView.layoutManager = LinearLayoutManager(this,
            LINEAR_LAYOUT_VERTICAL,
            false)
        contactRecyclerView.adapter = adapter
    }
}
