package cse512

import org.scalatest.Sequential

/**
 * Since scala suites are run in parallel, we want to avoid this for Spark as nasty things can happen
 */
class FunctionalTestSuites extends Sequential (
  new HotzoneAnalysisTest,
  new HotcellAnalysisTest,
  new SpatialQueryTest)
