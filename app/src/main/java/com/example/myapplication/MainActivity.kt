package com.example.myapplication

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: CatViewModel = viewModel(
                    it,
                    "CatViewModel",
                    CatViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                Main(viewModel)
            }
        }
    }
}

@Composable
fun Main(vm: CatViewModel = viewModel()) {
    val catList by vm.catList.observeAsState(listOf())
    Column {
        OutlinedTextField(
            vm.catNick,
            modifier= Modifier.padding(8.dp), label = { Text("Name") },
            onValueChange = {vm.changeName(it)})

        OutlinedTextField(
            vm.catAge.toString(),
            modifier= Modifier.padding(8.dp), label = { Text("Age") },
            onValueChange = {vm.changeAge(it)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button({ vm.addCat() },
            Modifier.padding(8.dp)) {Text("Add", fontSize = 22.sp)}
        CatList(cats = catList, delete = {vm.deleteUser(it)})
    }
}
@Composable
fun CatList(cats:List<Cat>, delete:(Int)->Unit) {
    LazyColumn(Modifier.fillMaxWidth()) {
        item{ CatTitleRow()}
        items(cats) { u -> CatRow(u, {delete(u.id)})  }
    }
}

@Composable
fun CatRow(cat:Cat, delete:(Int)->Unit) {
    Row(Modifier .fillMaxWidth().padding(5.dp)) {
        Text(cat.id.toString(), Modifier.weight(0.1f), fontSize = 22.sp)
        Text(cat.nick, Modifier.weight(0.2f), fontSize = 22.sp)
        Text(cat.age.toString(), Modifier.weight(0.2f), fontSize = 22.sp)
        Text("Delete", Modifier.weight(0.2f).clickable { delete(cat.id) },
            color=Color(0xFF6650a4), fontSize = 22.sp)
    }
}
@Composable
fun CatTitleRow() {
    Row(Modifier.background(Color.LightGray).fillMaxWidth().padding(5.dp)) {
        Text("Id", color = Color.White,
            modifier = Modifier.weight(0.1f), fontSize = 22.sp)
        Text("Name", color = Color.White,
            modifier = Modifier.weight(0.2f), fontSize = 22.sp)
        Text("Age", color = Color.White,
            modifier = Modifier.weight(0.2f), fontSize = 22.sp)
        Spacer(Modifier.weight(0.2f))
    }
}
