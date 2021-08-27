import { GET_IMAGE, UPLOAD_CONFIG } from "../constants/visualization";

export const uploadConfig = (payload) => ({
	type: UPLOAD_CONFIG,
	payload: payload
});

export const getImage = () => ({
	type: GET_IMAGE,
});
