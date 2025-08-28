package com.example.taskmanager
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskmanager.data.TaskEntity
@Composable
fun Edit(
    NavigateToEdit:()-> Unit,
    ItemToBeEdit: TaskEntity,
      viewModel: userViewModel = viewModel()
) {
    var edittitle by remember { mutableStateOf(ItemToBeEdit.title) }
    var editDesc by remember { mutableStateOf(ItemToBeEdit.description) }
    OutlinedTextField(
        value = edittitle,
        onValueChange = { edittitle = it },
    )
    OutlinedTextField(
        value = editDesc,
        onValueChange = { editDesc = it },
    )
    Button(
        onClick =
            {
                val update = ItemToBeEdit.copy(title = edittitle, description = editDesc)
                viewModel.update(update)
                NavigateToEdit()
            }
    )
    {
        Text("Save")

    }
}
