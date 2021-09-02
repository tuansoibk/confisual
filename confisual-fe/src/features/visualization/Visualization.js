import { useState } from 'react';
import { useDispatch, useSelector } from "react-redux";
import {clear, postConfig, selectDiagrams} from "./visualizationSlice";

export const Visualization = () => {
	const diagrams = useSelector(selectDiagrams);
	const dispatch = useDispatch();

	const [nevisAuthConfig, setNevisAuthConfig] = useState('');
	const onConfigChanged = (e) => setNevisAuthConfig(e.target.value);

	return (
		<div>
			<textarea onChange={onConfigChanged}>
			</textarea>
			<button
				type="button"
				onClick={() => dispatch(postConfig(nevisAuthConfig))}
			>
				Visualise
			</button>
			<button
				type="button"
				onClick={() => dispatch(clear())}
			>
				Clear
			</button>
				{ diagrams.length > 0 &&
					<div data-testid="diagrams">
						{
							diagrams.map((diagram, index) => (
								<img key={index} src={"data:image/png;base64,"+diagram}/>
							))
						}
					</div>
				}
		</div>
	);
}
