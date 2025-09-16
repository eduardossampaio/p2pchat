package com.apps.esampaio.p2pchat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.outlined.Add
//import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.esampaio.p2pchat.core.model.Author
import com.apps.esampaio.p2pchat.core.model.Chat
import com.apps.esampaio.p2pchat.core.model.Message
import com.apps.esampaio.p2pchat.core.viewModels.impl.ChatViewModel
import com.apps.esampaio.p2pchat.core.viewModels.impl.ChatViewModelState
import com.apps.esampaio.p2pchat.core.viewModels.impl.SetupProfileViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(chat: Chat, onNavigateBack: () -> Unit) {
    val viewModel = koinViewModel<ChatViewModel>()
    val state = viewModel.state.collectAsState()

    //startup
    LaunchedEffect(Unit) {
        viewModel.start(chat)
    }

    ChatScreenContent(
        state.value,
        chat,
        onNewMessage = {
            viewModel.sendMessage(it)
        },
        onNavigateBack = {

        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenContent(
    state: ChatViewModelState,
    chat: Chat,
    onNewMessage: (String) -> Unit = {},
    onNavigateBack: () -> Unit
) {


    var messages = remember { mutableStateListOf<Message>() }
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()




    Scaffold(
        topBar = {
            ChatTopBar(
                userName = chat.userName,
                userIp = "192.168.1.1",
                onNavigateBack = onNavigateBack
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                when (state) {
                    ChatViewModelState.Loading -> {

                    }

                    is ChatViewModelState.MessagesLoaded -> {
                        if(messages.isEmpty()) {
                            messages.addAll(state.messages.toMutableList())
                        }


                        LaunchedEffect(messages.size) {
                            if (messages.isNotEmpty()) {
                                listState.animateScrollToItem(messages.size - 1)
                            }
                        }
                    }

                    is ChatViewModelState.OnNewMessage -> {
                        if(!messages.contains(state.newMessage)) {
                            messages.add(state.newMessage)
                            LaunchedEffect(state.newMessage) {
                                listState.animateScrollToItem(messages.size - 1)
                            }
                        }
                    }
                }

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(messages,
//                        key = { it.id }
                    ) { message ->
                        MessageBubble(message = message)
                    }
                }

                ChatInput(
                    text = inputText,
                    onTextChange = { inputText = it },
                    onSendClick = {
                        if (inputText.isNotBlank()) {
                            onNewMessage.invoke(inputText)
                            inputText = ""
                        }
                    }
                )
            }


        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(userName: String, userIp: String, onNavigateBack: () -> Unit) {
    TopAppBar(
        title = {
            Column {
                Text(text = userName, fontWeight = FontWeight.Bold)
                Text(text = userIp, style = MaterialTheme.typography.bodySmall)
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}

@Composable
fun MessageBubble(message: Message) {
    val isFromMe = message.author == Author.ME
    val alignment = if (isFromMe) Alignment.CenterEnd else Alignment.CenterStart
    val bubbleColor =
        if (isFromMe) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
    val bubbleShape = if (isFromMe) {
        RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)
    } else {
        RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        Column(
            modifier = Modifier
                .clip(bubbleShape)
                .background(bubbleColor)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalAlignment = if (isFromMe) Alignment.End else Alignment.Start
        ) {
            Text(text = message.text)
            Text(
                text = message.timestamp,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun ChatInput(
    text: String,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Surface() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO: Lógica para abrir galeria de fotos */ }) {
                Icon(Icons.Outlined.Add, contentDescription = "Attach photo")
            }

            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Digite uma mensagem...") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(24.dp)
            )

            IconButton(
                onClick = onSendClick,
                enabled = text.isNotBlank(),
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send message",
                    tint = if (text.isNotBlank()) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreviewLoading() {
    MaterialTheme {
        ChatScreenContent(
            state = ChatViewModelState.Loading,
            chat = Chat(1, "Aline", "Olá, tudo bem?", "https://i.pravatar.cc/150?img=1"),
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreviewMessages() {
    MaterialTheme {
        ChatScreenContent(
            state = ChatViewModelState.MessagesLoaded(
                listOf(
                    Message(1, "Olá! Tudo bem?", Author.OTHER, "16:00"),
                    Message(2, "Tudo ótimo, e com você?", Author.ME, "16:01"),
                    Message(3, "Estou bem também. O que está fazendo?", Author.OTHER, "16:01"),
                    Message(4, "Estudando Jetpack Compose. É incrível!", Author.ME, "16:02"),
                    Message(5, "Que legal! Também quero aprender.", Author.OTHER, "16:03")
                )
            ),
            chat = Chat(1, "Aline", "Olá, tudo bem?", "https://i.pravatar.cc/150?img=1"),
            onNavigateBack = {}
        )
    }
}

