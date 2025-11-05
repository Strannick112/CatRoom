package com.example.myapplication

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
    }
}

@Composable
fun Main() {
    val owner = LocalViewModelStoreOwner.current

    owner?.let {
        val catViewModel: CatViewModel = viewModel(
            it,
            "CatViewModel",
            CatViewModelFactory(LocalContext.current.applicationContext as Application)
        )

        val breedViewModel: BreedViewModel = viewModel(
            it,
            "BreedViewModel",
            BreedViewModelFactory(LocalContext.current.applicationContext as Application)
        )

        val breedWithCatsViewModel: BreedWithCatsViewModel = viewModel(
            it,
            "BreedWithCatsViewModel",
            BreedWithCatsVMFactory(LocalContext.current.applicationContext as Application)
        )

        var page: Int by remember { mutableStateOf(0) }
        Column() {
            Row() {
                Button(onClick = { page = 0 }) { Text("Cats") }
                Button(onClick = { page = 1 }) { Text("Breed") }
                Button(onClick = { page = 2 }) { Text("B.vsC.") }
            }
            if (page == 0)
                CatsPanel(catViewModel)
            if(page == 1)
                BreedsPanel(breedViewModel)
            if(page == 2)
                BreedWithCatsPanel(breedWithCatsViewModel)
        }
    }
}

@Composable
fun CatsPanel(vm: CatViewModel = viewModel()){
    val catList by vm.catList.observeAsState(listOf())
    Column {
        AddPanel(listOf(
            TableInfo(vm.catId.toString(), "Id", {vm.changeId(it)}),
            TableInfo(vm.catNick, "Name", {vm.changeName(it)}),
            TableInfo(vm.catAge.toString(), "Age", onValueChange = {vm.changeAge(it)})
        ), { vm.updateCat() }, { vm.addCat() })

        CatList(cats = catList, delete = {vm.deleteUser(it)})
    }
}

@Composable
fun BreedsPanel(vm: BreedViewModel = viewModel()){
    val breedList by vm.breedList.observeAsState(listOf())
    Column {
        AddPanel(listOf(
            TableInfo(vm.id.toString(), "Id", {vm.changeId(it)}),
            TableInfo(vm.title, "Title", {vm.changeTitle(it)}),
            TableInfo(vm.description, "Desc", onValueChange = {vm.changeDescription(it)})
        ), { vm.add() }, {  })

        BreedList(breeds = breedList, delete = { })
    }
}

@Composable
fun BreedWithCatsPanel(vm: BreedWithCatsViewModel = viewModel()){
    val breedWithCatsList by vm.breedWithCatsList.observeAsState(listOf())
    Column {
//        AddPanel(listOf(
//            TableInfo(vm.catId.toString(), "Id", {vm.changeId(it)}),
//            TableInfo(vm.catNick, "Name", {vm.changeName(it)}),
//            TableInfo(vm.catAge.toString(), "Age", onValueChange = {vm.changeAge(it)})
//        ), { vm.updateCat() }, { vm.addCat() })

        CatList(cats = breedWithCatsList, delete = { })
    }
}

@Composable
fun BreedWithCatsList(breedWithCats:List<BreedWithCats>) {
    LazyColumn(Modifier.fillMaxWidth()) {
        item{ TitleRow("Id", "Name", "Age")}
        items(breedWithCats) { breedWithCat ->
            JustRow(cat.catId.toString(), cat.nick, cat.age.toString(), delete = { })
        }
    }
}


data class TableInfo(val value: String, val header: String, val onValueChange: (String)->Unit)

@Composable
fun AddPanel(tableInfoes: List<com.example.myapplication.TableInfo>,
             onAdd: () -> Unit, onUpdate: () -> Unit){
    Column{
        tableInfoes.forEach {
            row ->
            OutlinedTextField(
                row.value,
                modifier= Modifier.padding(8.dp), label = { Text(row.header) },
                onValueChange = {row.onValueChange(it)})
        }
        Row(){
            Button({ onUpdate() },
                Modifier.padding(8.dp)) {Text("Update", fontSize = 22.sp)}
            Button({ onAdd() },
                Modifier.padding(8.dp)) {Text("Add", fontSize = 22.sp)}
        }
    }

}


@Composable
fun CatList(cats:List<Cat>, delete:(Int)->Unit) {
    LazyColumn(Modifier.fillMaxWidth()) {
        item{ TitleRow("Id", "Name", "Age")}
        items(cats) { cat ->
            JustRow(cat.catId.toString(), cat.nick, cat.age.toString(), delete = {delete(cat.catId)})
        }
    }
}

@Composable
fun BreedList(breeds:List<Breed>, delete:(Int)->Unit) {
    LazyColumn(Modifier.fillMaxWidth()) {
        item{ TitleRow("Id", "Title", "Desc")}
        items(breeds) { breed ->
            JustRow(breed.breedId.toString(), breed.title, breed.description, delete = {delete(breed.breedId)})
        }
    }
}

@Composable
fun JustRow(vararg column: String, delete:()->Unit) {
    Row(Modifier.fillMaxWidth().padding(5.dp)) {
        column.forEachIndexed {
            index, value ->
            val weight: Float = if(index == 0) 0.1f else 0.2f
            ColumnText(value, Modifier.weight(weight))
        }
        Text("Delete",
            Modifier.weight(0.2f)
                .clickable { delete() },
            color=Color(0xFF6650a4), fontSize = 22.sp)
    }
}

@Composable
fun ColumnText(text: String, modifier: Modifier, color: Color = Color.Black) {
    Text(text, fontSize = 22.sp, modifier = modifier, color = color)
}

@Composable
fun TitleRow(vararg titles: String) {
    Row(Modifier.background(Color.LightGray).fillMaxWidth().padding(5.dp)) {
        titles.forEachIndexed {
            index, value ->
            val weight: Float = if(index == 0) 0.1f else 0.2f
            ColumnText(value, modifier = Modifier.weight(weight), Color.White)
        }
        Spacer(Modifier.weight(0.2f))
    }
}
