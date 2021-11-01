import Visualiser from "./Visualiser";
import { VisualisationAPI } from "../../api/VisualisationAPI";
import { unmountComponentAtNode } from "react-dom";
import { fireEvent, render, waitFor } from "@testing-library/react";

let container = null;
let wrapper = null;
beforeEach( () => {
  container = document.createElement("div");
  document.body.appendChild(container);
  wrapper = render(<Visualiser domain="nevisAuth"/>, container);
});

afterEach(() => {
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});

it("should visualise nevisAuth diagrams", async () => {
  // given
  const visualiseMock = jest.spyOn(VisualisationAPI, 'visualise');
  visualiseMock.mockResolvedValue({
    diagrams: {
      "domain": "diagram"
    }
  })

  // when
  await waitFor(() => {
    fireEvent.click(wrapper.getByTestId("visualisationButton"));
  })

  // then
  expect(wrapper.getByTestId("domain tab")).toBeTruthy();
  expect(wrapper.getByTestId("domain diagram")).toBeTruthy();
  expect(() => wrapper.getByTestId("errorMessage")).toThrow('Unable to find an element');
});

it("should throw error", async () => {
  // given
  const visualiseMock = jest.spyOn(VisualisationAPI, 'visualise');
  visualiseMock.mockResolvedValue({
    errors: "Can't visualise nevisAuth diagram"
  })

  // when
  await waitFor(() => {
    fireEvent.click(wrapper.getByTestId("visualisationButton"));
  })

  // then
  expect(wrapper.getByTestId("errorMessage")).toBeTruthy();
});

it("should clear diagrams", async () => {
  // given
  const visualiseMock = jest.spyOn(VisualisationAPI, 'visualise');
  visualiseMock.mockResolvedValue({
    diagrams: {
      "domain": "diagram"
    }
  })

  // when
  await waitFor(() => {
    fireEvent.click(wrapper.getByTestId("visualisationButton"));
  })

  await waitFor(() => {
    fireEvent.click(wrapper.getByTestId("clearButton"));
  })

  // then
  expect(() => wrapper.getByTestId("errorMessage")).toThrow('Unable to find an element');
  expect(() => wrapper.getByTestId("domain tab")).toThrow('Unable to find an element');
  expect(() => wrapper.getByTestId("domain diagrams")).toThrow('Unable to find an element');
});
