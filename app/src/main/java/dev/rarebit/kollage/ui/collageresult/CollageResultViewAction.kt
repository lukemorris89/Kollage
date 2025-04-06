package dev.rarebit.kollage.ui.collageresult

import dev.rarebit.core.viewmodel.BaseViewAction
import dev.rarebit.design.component.tools.CollageTool
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.LayerColour
import dev.rarebit.kollage.ui.createcollage.util.imageutil.BackgroundSelection
import dev.rarebit.kollage.ui.createcollage.util.imageutil.ImageFormat

sealed class CollageResultViewAction : BaseViewAction() {
    data object OnBackPressed : CollageResultViewAction()
    data class OnToolClicked(val tool: CollageTool) : CollageResultViewAction()
    data object OnSaveClicked : CollageResultViewAction()
    data class OnUpdateImageFormat(val imageFormat: ImageFormat) : CollageResultViewAction()
    data class OnUpdateBackground(val backgroundSelection: BackgroundSelection) : CollageResultViewAction()
    data class OnUpdateBackgroundColour(val colour: LayerColour) : CollageResultViewAction()
}
