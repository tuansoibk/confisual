import { setupServer } from "msw/node";
import { handlers } from "../../api/server";
import { createRender, fireEvent, screen } from "../../utils/testUtil";
import visualizeReducer from "./visualizationSlice";
import { Visualization } from "./Visualization";


const server = setupServer(...handlers);

beforeAll(() => server.listen())

afterEach(() => server.resetHandlers())

afterAll(() => server.close())

describe('visualization Action', () => {
	let render;
	beforeEach(() => {
		const reducer = {
			visualization: visualizeReducer
		}
		render = createRender(reducer);
	});

	test('Should visualize nevisAuthConfig', async () => {
		// given
		render(<Visualization/>);

		// when
		fireEvent.click(screen.getByRole('button', { name: /Visualise/ }))

		// then
		const diagrams = await screen.findByTestId("diagrams");
		expect(diagrams.childNodes.length).toBeGreaterThan(0);
	})
});

