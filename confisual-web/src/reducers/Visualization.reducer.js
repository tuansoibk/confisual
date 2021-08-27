import { GET_IMAGE, UPLOAD_CONFIG } from "../constants/visualization";
import * as HttpUtil from '../utils/Http.util'

const VisualizationReducer = (state = {
	nevisAuthConfig: "",
	progress: 0,
	message: "",
	fileInfos: [],
}, action) => {
	switch (action.type) {
		case UPLOAD_CONFIG:
			console.log("upload file");
			return upload(state);
		case GET_IMAGE:
			console.log('get file');
			return getFiles();
		default:
			return state;
	}
};

const upload = (state) => {
	state.progress = 0;

	return HttpUtil.uploadConfigFile(state.nevisAuthConfig, (event) => {
		state.progress = Math.round((100 * event.loaded) / event.total);
	}).then((response) => {
		state.nevisAuthConfig = "";
		state.message = response.data.message;
		return state;
	})
}

const getFiles = (state) => {
	return HttpUtil.getFiles()
		.then((response) => {
			state.fileInfos = response.data;
			return state;
		});
}

export default VisualizationReducer;
