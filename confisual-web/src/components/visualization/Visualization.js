import { Component } from "react";
import { Button, Card, CardActions, CardContent, CircularProgress, Grid, TextField } from "@material-ui/core";
import * as visualizationAction from '../../actions/Visualization.action'
import { connect } from "react-redux";
import './Visualization.css'

class Visualization extends Component {
	constructor() {
		super();
		this.state = {
			nevisAuthConfig: ""
		};
		this.handleTextChange = this.handleTextChange.bind(this);
		this.sendNevisAuthConfig = this.sendNevisAuthConfig.bind(this);
	}

	handleTextChange(e) {
		this.setState({
			nevisAuthConfig: e.target.value
		});
	}

	sendNevisAuthConfig() {
		this.props.uploadConfig(this.state.nevisAuthConfig);
		this.props.getFiles();
	}

	render() {
		return (
			<Grid
				container
				direction="row"
				justifyContent="center"
			>
				<Grid item xs={5}>
						<textarea
							className="input-text"
							onChange={this.handleTextChange}
							cols={10}
						>
						</textarea>
				</Grid>

				<Grid item xs={2}>
					<CircularProgress
						variant="determinate"
						value={this.props.progress}
					/>
					<Button
						variant="contained"
						disabled={this.state.nevisAuthConfig === ""}
						onClick={this.sendNevisAuthConfig}
					/>
				</Grid>

				<Grid item xs={5}>
					<div className="output card">
						<div className="alert alert-light" role="alert">
							{this.props.message}
						</div>
						<Card>
							<CardContent>
								List of Files
							</CardContent>
							{this.props.fileInfos &&
							this.props.fileInfos.map((file, index) => (
								<CardActions>
									<a href={file.url}>{file.name}</a>
								</CardActions>
							))}
						</Card>
					</div>
				</Grid>
			</Grid>
		)
	};
}

const mapStateToProps = (state) => {
	return {
		nevisAuthConfig: state.nevisAuthConfig,
		progress: state.progress,
		message: state.message,
		fileInfos: state.fileInfos
	};
};

const mapDispatchToProps = (dispatch) => {
	return {
		uploadConfig: nevisAuthConfig => dispatch(visualizationAction.uploadConfig(nevisAuthConfig)),
		getFiles: () => dispatch(visualizationAction.getImage())
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(Visualization);
