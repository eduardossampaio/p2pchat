

package com.apps.esampaio.p2pchat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.apps.esampaio.p2pchat.core.model.Chat
import com.apps.esampaio.p2pchat.core.viewModels.impl.ChatListScreenViewModel
import com.apps.esampaio.p2pchat.core.viewModels.impl.ChatListScreenViewModelState
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(onChatClick: (Chat) -> Unit) {
    val viewModel = koinViewModel<ChatListScreenViewModel>()
    val state = viewModel.state.collectAsState()

    //startup
    LaunchedEffect(Unit){
        viewModel.start()
    }

    ChatListScreenContent(state.value, onNewChatClick = { ip ->
        viewModel.startNewChat(ip)
    }, onChatClick)


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreenContent(
    state: ChatListScreenViewModelState,
    onNewChatClick: (ip: String) -> Unit,
    onChatClick: (Chat) -> Unit) {



    var showDialog by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chats") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            if(state is ChatListScreenViewModelState.Loading){
                return@Scaffold
            }
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add new chat")
            }
        }
    ) { paddingValues ->

        if (showDialog) {
            AddChatDialog(
                onDismiss = { showDialog = false },
                onConfirm = { ipAddress ->
                    // TODO: Lógica para conectar com o IP informado
                    println("Connecting to IP: $ipAddress")
                    showDialog = false
                    onNewChatClick.invoke(ipAddress)
                }
            )
        }

            when (state) {
                is ChatListScreenViewModelState.ChatsFound -> {
                    val chatList = state.chats
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        items(chatList) { chat ->
                            ChatListItem(chat = chat, onClick = {
                                // TODO: Lógica para abrir a tela de conversa específica
                                println("Clicked on chat with ${chat.userName}")
                                onChatClick.invoke(chat)
                            })
                            HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
                        }
                    }
                }

                ChatListScreenViewModelState.Loading -> {
                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        CircularProgressIndicator()
                    }
                }

                ChatListScreenViewModelState.NoChatsFound -> {
                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                      Text("No chats found")
                    }
                }

        }
    }
}

@Composable
fun ChatListItem(chat: Chat, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberAsyncImagePainter(model = chat.avatarUrl),
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))


        Column {
            Text(
                text = chat.userName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = chat.lastMessage,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun AddChatDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var ipAddress by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Connection") },
        text = {
            OutlinedTextField(
                value = ipAddress,
                onValueChange = { ipAddress = it },
                label = { Text("User IP Address") },
                placeholder = { Text("e.g., 192.168.0.1") },
                singleLine = true
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (ipAddress.isNotBlank()) {
                        onConfirm(ipAddress)
                    }
                }
            ) {
                Text("Connect")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
@Preview
private fun ChatListScreenPreview() {
    ChatListScreenContent(ChatListScreenViewModelState.Loading, {},{

    })
}

@Composable
@Preview
private fun EmptyChatListScreenPreview() {
    ChatListScreenContent(ChatListScreenViewModelState.NoChatsFound, {},{

    })
}