import { combineReducers } from "@reduxjs/toolkit";
import VisualizationReducer from "./Visualization.reducer";

export default combineReducers({
	visualization: VisualizationReducer
});
