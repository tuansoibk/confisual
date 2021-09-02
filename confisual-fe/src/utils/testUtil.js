// test-utils.jsx
import React from 'react'
import { render as rtlRender } from '@testing-library/react'
import { configureStore } from '@reduxjs/toolkit'
import { Provider } from 'react-redux'

const createRender = (reducer) => (
	ui,
	{
		preloadedState,
		store = configureStore({ reducer: reducer, preloadedState }),
		...renderOptions
	} = {}
) => {
	function Wrapper({ children }) {
		return <Provider store={store}>{children}</Provider>
	}
	return rtlRender(ui, { wrapper: Wrapper, ...renderOptions })
}

// re-export everything
export * from '@testing-library/react'
// override render method
export { createRender }
