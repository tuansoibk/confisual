import { useState } from 'react';
import { useDispatch, useSelector } from "react-redux";
import {clear, postConfig, selectDiagrams} from "./visualizationSlice";
import { Button, Grid } from "@material-ui/core";

export const Visualization = () => {
	const diagrams = useSelector(selectDiagrams);
	const error = useSelector(state => state.visualization.error)
	const dispatch = useDispatch();

	const [nevisAuthConfig, setNevisAuthConfig] = useState('');
	const onConfigChanged = (e) => setNevisAuthConfig(e.target.value);

	return (
		<div>
			<Grid item>
				<textarea
					id="input"
					onChange={onConfigChanged}>
				</textarea>
			</Grid>

			<Grid>
				<Button
					variant="contained"
					onClick={() => dispatch(postConfig(nevisAuthConfig))}
				>
					Visualise
				</Button>
				<Button
					variant="contained"
					onClick={() => dispatch(clear())}
				>
					Clear
				</Button>
			</Grid>

			<Grid>
				{ error !== undefined && <div>{error}</div>}
				{ diagrams.length > 0 &&
				<div data-testid="diagrams">
						{
							diagrams.map((diagram) => (
									<img src={"data:image/png;base64,"+diagram}/>
							))
						}
				</div>
				}
			</Grid>
		</div>
	);
}
