/// <reference path="../../../typings/tsd.d.ts"/>

import "angular";

export class SitemapDirective implements ng.IDirective {
  restrict = 'E';
  templateUrl = 'user/project/projectSitemap.html'
  replace = true;
}
