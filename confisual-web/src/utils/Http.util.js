import axios from "axios";

const connection = axios.create({
	baseURL: "http://localhost:8000",
	headers: {
		"Content-Type": "application/json"
	}
});

export const uploadConfigFile = (configFile, onUploadProgress) => {
	return connection.post("/upload", configFile, {
		headers: {
			"Content-Type": "application/xml",
		},
		onUploadProgress,
	});
};

export const getFiles = () => {
	return connection.get("/files");
};
