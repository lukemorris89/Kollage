package dev.rarebit.kollage.ui.gallery.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.rarebit.design.theme.LightGrey
import dev.rarebit.design.theme.White
import dev.rarebit.kollage.data.model.Collage
import dev.rarebit.design.R as DR

@Composable
fun CollageThumbnail(
    collage: Collage,
    isSelectMode: Boolean,
    selected: Boolean,
    onClick: () -> Unit,
    onLongPress: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(LightGrey)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongPress
            )
            .then(
                if (isSelectMode) {
                    Modifier.border(
                        BorderStroke(
                            width = 1.dp,
                            color = White,
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.BottomEnd,
    ) {
        AsyncImage(
            model = collage.uri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        if (isSelectMode) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.BottomEnd,
            ) {
                Icon(
                    modifier = Modifier
                        .size(18.dp),
                    painter = painterResource(
                        id = if (selected) {
                            DR.drawable.ic_check_circle
                        } else {
                            DR.drawable.ic_circle
                        }
                    ),
                    tint = White,
                    contentDescription = null,
                )
            }
        }
    }
}