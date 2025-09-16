package com.apps.esampaio.p2pchat.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apps.esampaio.p2pchat.R
import com.apps.esampaio.p2pchat.core.model.User
import com.apps.esampaio.p2pchat.core.viewModels.impl.SetupProfileViewModel
import com.apps.esampaio.p2pchat.core.viewModels.impl.SetupProfileViewModelState
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ProfileScreen(onCreateProfile: (user: User) -> Unit){

    val viewModel = koinViewModel<SetupProfileViewModel>()
    val state = viewModel.state.collectAsState()

    //startup
    LaunchedEffect(Unit){
        viewModel.start()
    }

    state.value.let { value ->
        if (value is SetupProfileViewModelState.UserCreated) {
            val user = value.user
            LaunchedEffect(Unit) {
                onCreateProfile.invoke(user)
            }
        }
    }

    Scaffold { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            ProfileCreationScreen(state.value){ userName, profilePicture ->
                viewModel.createUser(User(userName,profilePicture))
            }
        }
    }
}

@Composable
fun ProfileCreationScreen(
    state: SetupProfileViewModelState,
    onCreateProfile: (userName: String, profilePicture:String?) -> Unit
){

    var username by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }


    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
        }
    )

    if(state is SetupProfileViewModelState.UserCreated){
        onCreateProfile.invoke(username,imageUri?.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.create_your_profile),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )


        ImageSelector(
            imageUri = imageUri,
            onImageClick = {
                imagePickerLauncher.launch("image/*")
            }
        )

        Spacer(modifier = Modifier.height(32.dp))


        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = {
                Text(
                    text = stringResource(R.string.username),
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )


        Spacer(modifier = Modifier.weight(1f))


        Button(
            enabled = username.length >= 3,
            onClick = {
                onCreateProfile.invoke(username,imageUri?.toString())
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(stringResource(R.string.create))
        }
    }
}

@Composable
fun ImageSelector(imageUri: Uri?, onImageClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .clickable { onImageClick() },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image Preview",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Photo Icon",
                    modifier = Modifier.size(40.dp)
                )
                Text(stringResource(R.string.select_image), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
@Preview
private fun ProfileScreenPreview() {
    ProfileScreen(){ _->

    }
}