package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.ui.MainViewModel

@Composable
fun CharacterDetailView(viewModel: MainViewModel, characterId: String, popBack: () -> Unit) {
    val (character, setCharacter) = remember { mutableStateOf<CharacterDetail?>(null) }

    LaunchedEffect(characterId) {
        setCharacter(viewModel.getCharacter(characterId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(character?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        })
    {
        Surface(color = Color.LightGray) {

            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                character?.let {

                    item(character.id) {

                        Text(
                            "Mugshot", style = typography.h5, color = LocalContentColor.current,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )

                        Surface(color = Color.White) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                val imageUrl = character.image
                                Card(
                                    modifier = Modifier.size(150.dp),
                                    shape = RoundedCornerShape(25.dp)
                                ) {
                                    Image(painter = rememberImagePainter(imageUrl),
                                        contentDescription = character.name
                                    )
                                }
                            }
                        }


                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            "Episodes", style = typography.h5, color = LocalContentColor.current,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )

                        Surface(color = Color.White) {
                            CharacterEpisodeList(character)
                        }

                    }

                }
            }

        }

    }

}

@Composable
private fun CharacterEpisodeList(character: CharacterDetail) {

    Column(modifier = Modifier.padding(horizontal = 16.dp),) {
        character.episode.let { episodeList ->
            episodeList.filterNotNull().forEach { episode ->
                Column {
                    Text(
                        episode.name,
                        style = typography.h6)
                    Text(
                        episode.air_date,
                        style = typography.subtitle2,
                        color = Color.Gray)
                }
                Divider()
            }
        }
    }
}

