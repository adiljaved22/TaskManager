/*
composable("edit/{taskId}") { backStackEntry ->
    val taskId = backStackEntry.arguments?.getString("taskId")?.toInt() ?: 0
    val task = userViewModel.getall.collectAsState(initial = emptyList()).value
        .firstOrNull { it.id == taskId }

    task?.let {
        Edit(
            itemToBeEdit = it,
            onEditComplete = { navController.popBackStack() },
            NavigateToEdit = { navController.popBackStack() },
            viewModel = userViewModel
        )
    }
}
*/
