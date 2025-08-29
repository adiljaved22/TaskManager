/*
IconButton(onClick = { dialogBox = true; selectedTaskId = task.id }) { ... }

if (dialogBox) {
    AlertDialog(
        onDismissRequest = { dialogBox = false },
        title = { Text("Are you want To delete this item? ") },
        confirmButton = {
            Button(onClick = {
                selectedTaskId?.let { viewModel.delete(it) }
                dialogBox = false
            }) { Text("Confirm") }
        },
        dismissButton = {
            Button(onClick = { dialogBox = false }) { Text("Dismiss") }
        }
    )
}
*/
